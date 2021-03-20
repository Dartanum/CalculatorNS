package com.example.calculator;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;

public class MainActivity extends Activity implements View.OnClickListener {
    int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    Button div, mul, sub, add, one, two, three,
            four, five, six, seven, eight, nine,
            zero, del, AC, symA, symB,
            symC, symD, symE, symF;
    ImageButton result;
    TextView A, B, ssA, ssB, sign, ssRes, total, textEqual, textB, textC;
    TextView select, lastSelect;
    boolean isNewSelect = false;
    String strSsA, strSsB, strSsRes, strA, strB, strSign;
    int intSsA, intSsB, intSsRes;
    int widthExpression, widthLayout, widthA, widthB, widthC, widthAB, widthBC, widthSym;
    RelativeLayout rl;
    RelativeLayout.LayoutParams lpSign, lpB, lpA, lpTextEqual, lpTotal, lpB2, lpTextEqual2, lpTextEqual3;
    Display display;
    DisplayMetrics metricsB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //нахождение элементов по ID
        {
            div = findViewById(R.id.div);
            mul = findViewById(R.id.mul);
            sub = findViewById(R.id.sub);
            add = findViewById(R.id.add);
            one = findViewById(R.id.one);
            two = findViewById(R.id.two);
            three = findViewById(R.id.three);
            four = findViewById(R.id.four);
            five = findViewById(R.id.five);
            six = findViewById(R.id.six);
            seven = findViewById(R.id.seven);
            eight = findViewById(R.id.eight);
            nine = findViewById(R.id.nine);
            zero = findViewById(R.id.zero);
            del = findViewById(R.id.del);
            AC = findViewById(R.id.AC);
            A = findViewById(R.id.A);
            B = findViewById(R.id.B);
            ssA = findViewById(R.id.ssA);
            ssB = findViewById(R.id.ssB);
            sign = findViewById(R.id.sign);
            ssRes = findViewById(R.id.ssRes);
            result = findViewById(R.id.result);
            symA = findViewById(R.id.symA);
            symB = findViewById(R.id.symB);
            symC = findViewById(R.id.symC);
            symD = findViewById(R.id.symD);
            symE = findViewById(R.id.symE);
            symF = findViewById(R.id.symF);
            total = findViewById(R.id.total);
            textEqual = findViewById(R.id.textEqual);
            textB = findViewById(R.id.textB);
            textC = findViewById(R.id.textC);
            rl = findViewById(R.id.rl);
        }
        //привязка элементам обработчиков
        {
            one.setOnClickListener(this);
            two.setOnClickListener(this);
            three.setOnClickListener(this);
            four.setOnClickListener(this);
            five.setOnClickListener(this);
            six.setOnClickListener(this);
            seven.setOnClickListener(this);
            eight.setOnClickListener(this);
            nine.setOnClickListener(this);
            zero.setOnClickListener(this);
            div.setOnClickListener(this);
            mul.setOnClickListener(this);
            sub.setOnClickListener(this);
            add.setOnClickListener(this);
            del.setOnClickListener(this);
            AC.setOnClickListener(this);
            result.setOnClickListener(this);
            symA.setOnClickListener(this);
            symB.setOnClickListener(this);
            symC.setOnClickListener(this);
            symD.setOnClickListener(this);
            symE.setOnClickListener(this);
            symF.setOnClickListener(this);

            A.setOnClickListener(clickTV);
            B.setOnClickListener(clickTV);
            ssA.setOnClickListener(clickTV);
            ssB.setOnClickListener(clickTV);
            ssRes.setOnClickListener(clickTV);
        }
        //установка значений по умолчанию
        {
            select = A;
            lastSelect = A;
            strSsA = ssA.getText().toString();
            strSsB = ssB.getText().toString();
            strSsRes = ssRes.getText().toString();
            strA = A.getText().toString();
            strB = B.getText().toString();
            strSign = sign.getText().toString();
            intSsA = Integer.parseInt(strSsA);
            intSsB = Integer.parseInt(strSsB);
            intSsRes = Integer.parseInt(strSsRes);
            display = getWindowManager().getDefaultDisplay();
            metricsB = new DisplayMetrics();
            display.getMetrics(metricsB);
            widthSym = 53;
        }
        //заполнение параметров элементов, зависящих от layout
        {
            lpSign = ((RelativeLayout.LayoutParams) sign.getLayoutParams());
            lpB = ((RelativeLayout.LayoutParams) B.getLayoutParams());
            lpA = ((RelativeLayout.LayoutParams) A.getLayoutParams());
            lpTextEqual = ((RelativeLayout.LayoutParams) textEqual.getLayoutParams());
            lpTotal = ((RelativeLayout.LayoutParams) total.getLayoutParams());
            widthLayout = metricsB.widthPixels;
            //Перенос В с 1 строки на 2
            lpB2 = new RelativeLayout.LayoutParams(WC, WC);
            lpB2.addRule(RelativeLayout.BELOW, R.id.A);
            lpB2.addRule(RelativeLayout.ALIGN_START, R.id.A);
            //Перенос С с первой строки на 2
            lpTextEqual2 = new RelativeLayout.LayoutParams(WC, WC);
            lpTextEqual2.addRule(RelativeLayout.BELOW, R.id.A);
            lpTextEqual2.addRule(RelativeLayout.ALIGN_START, R.id.A);
            //Перенос С со 2 строки на 3
            lpTextEqual3 = new RelativeLayout.LayoutParams(WC, WC);
            lpTextEqual3.addRule(RelativeLayout.BELOW, R.id.B);
            lpTextEqual3.topMargin = 60;
            lpTextEqual3.addRule(RelativeLayout.ALIGN_START, R.id.B);
        }
        //Ширина выражения
        widthExpression = A.getWidth() + ssA.getWidth() + lpSign.getMarginStart() + B.getWidth() + lpB.getMarginStart() +
                ssB.getWidth() + textEqual.getWidth() + lpTextEqual.getMarginStart() + total.getWidth() +
                lpTotal.getMarginStart() + ssRes.getWidth() + lpA.leftMargin + sign.getWidth();
        widthA = lpA.leftMargin + A.getWidth() + ssA.getWidth() + lpSign.getMarginStart();
        widthB = B.getWidth() + ssB.getWidth() + lpTextEqual.getMarginStart();
        widthAB = widthA + sign.getWidth() + lpB.getMarginStart() + widthB;
        widthC = total.getWidth() + ssRes.getWidth();
        widthBC = widthB + textEqual.getWidth() + lpTotal.getMarginStart() + widthC;
    }

    @Override
    public void onClick(View v) {
        String text = select.getText().toString();
        reconstruct();
        //Обработка ввода цифр и букв
        {
            boolean isSs = false; //является ли системой счисления
            boolean isSize2 = false; //имеет ли размер в 2 символа
            boolean firstChar1 = false; //если первый символ единица
            boolean canEditSs = false; //можно ли изменять эту систему счисления
            boolean isSize0 = false; //если пусто
            int lengthSs = text.length();

            if (select == ssA || select == ssB || select == ssRes) {
                isSs = true;
                if (lengthSs >= 2)
                    isSize2 = true;
                else if (lengthSs == 0)
                    isSize0 = true;
                if (lengthSs == 1 && text.equals("1"))
                    firstChar1 = true;
            }
            canEditSs = isSs && !isSize2;
            switch (v.getId()) {
                case R.id.zero:
                    if (!isSs || (canEditSs && firstChar1))
                        select.setText(text + "0");
                    break;
                case R.id.one:
                    if (!isSs || (canEditSs && (firstChar1 || isSize0)))
                        select.setText(text + "1");
                    break;
                case R.id.two:
                    if (!isSs || (canEditSs && (firstChar1 || isSize0)))
                        select.setText(text + "2");
                    break;
                case R.id.three:
                    if (!isSs || (canEditSs && (firstChar1 || isSize0)))
                        select.setText(text + "3");
                    break;
                case R.id.four:
                    if (!isSs || (canEditSs && (firstChar1 || isSize0)))
                        select.setText(text + "4");
                    break;
                case R.id.five:
                    if (!isSs || (canEditSs && (firstChar1 || isSize0)))
                        select.setText(text + "5");
                    break;
                case R.id.six:
                    if (!isSs || (canEditSs && (firstChar1 || isSize0)))
                        select.setText(text + "6");
                    break;
                case R.id.seven:
                    if (!isSs || (canEditSs && isSize0))
                        select.setText(text + "7");
                    break;
                case R.id.eight:
                    if (!isSs || (canEditSs && isSize0))
                        select.setText(text + "8");
                    break;
                case R.id.nine:
                    if (!isSs || (canEditSs && isSize0))
                        select.setText(text + "9");
                    break;
                case R.id.symA:
                    if (!isSs)
                        select.setText(text + "A");
                    break;
                case R.id.symB:
                    if (!isSs)
                        select.setText(text + "B");
                    break;
                case R.id.symC:
                    if (!isSs)
                        select.setText(text + "C");
                    break;
                case R.id.symD:
                    if (!isSs)
                        select.setText(text + "D");
                    break;
                case R.id.symE:
                    if (!isSs)
                        select.setText(text + "E");
                    break;
                case R.id.symF:
                    if (!isSs)
                        select.setText(text + "F");
                    break;
            }
        }
        //Добавление знака
        switch (v.getId()) {
            case R.id.div:
                sign.setText("/");
                break;
            case R.id.mul:
                sign.setText("*");
                break;
            case R.id.add:
                sign.setText("+");
                break;
            case R.id.sub:
                sign.setText("-");
                break;
        }
        updateTv();
        //Обработка результата
        {
            try {
                if (v.getId() == R.id.result) {
                    boolean NaN = false;
                    BigInteger answer = new BigInteger("0");
                    BigInteger a = new BigInteger(strA, intSsA);
                    BigInteger b = new BigInteger(strB, intSsB);
                    if (strSign.equals("+"))
                        answer = a.add(b);
                    else if (strSign.equals("-"))
                        answer = a.subtract(b);
                    else if (strSign.equals("*"))
                        answer = a.multiply(b);
                    else if (strSign.equals("/"))
                        try {
                            answer = a.divide(b);
                        } catch (ArithmeticException e) {
                            Toast.makeText(this, "Делить на ноль нельзя", Toast.LENGTH_SHORT).show();
                            NaN = true;
                        }
                    if (!NaN) {
                        total.setText(answer.toString(intSsRes).toUpperCase());
                    } else total.setText("NaN");
                    reconstruct();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Несоответствие системы счисления значению", Toast.LENGTH_SHORT).show();
            }
        }
        //Удаление одного символа
        if (v.getId() == del.getId() && select.getText().length() != 0) {
            select.setText(select.getText().toString().substring(0, select.length() - 1));
        }
        //Удаление всех символов
        if (v.getId() == AC.getId()) {
            select.setText("");
        }
        reconstruct();
    }

    //Обработка нажатия на TextView
    View.OnClickListener clickTV = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            strSsA = ssA.getText().toString();
            strSsB = ssB.getText().toString();
            strSsRes = ssRes.getText().toString();
            strA = A.getText().toString();
            strB = B.getText().toString();
            if (isNewSelect)
                lastSelect = select;
            select = (TextView) v;
            if (lastSelect == select) {
                isNewSelect = false;
            } else {
                isNewSelect = true;
            }
            if (isNewSelect) {
                lastSelect.setTextColor(Color.BLACK);
                if (lastSelect == A && strA.isEmpty())
                    A.setText("0");
                else if (lastSelect == B && strB.isEmpty())
                    B.setText("0");
                else if (lastSelect == ssA && (strSsA.isEmpty() || strSsA.equals("1")))
                    ssA.setText("10");
                else if (lastSelect == ssB && (strSsB.isEmpty() || strSsB.equals("1")))
                    ssB.setText("10");
                else if (lastSelect == ssRes && (strSsRes.isEmpty() || strSsRes.equals("1")))
                    ssRes.setText("10");
            }
            select.setTextColor(Color.parseColor("#FF5722"));
        }
    };

    public void updateTv() {
        strSsA = ssA.getText().toString().isEmpty() ? "10" : ssA.getText().toString();
        strSsB = ssB.getText().toString().isEmpty() ? "10" : ssB.getText().toString();
        strSsRes = ssRes.getText().toString().isEmpty() ? "10" : ssRes.getText().toString();
        strA = A.getText().toString().isEmpty() ? "0" : A.getText().toString();
        strB = B.getText().toString().isEmpty() ? "0" : B.getText().toString();
        strSign = sign.getText().toString().isEmpty() ? "+" : sign.getText().toString();
        intSsA = strSsA.isEmpty() ? 0 : Integer.parseInt(strSsA);
        intSsB = strSsB.isEmpty() ? 0 : Integer.parseInt(strSsB);
        intSsRes = strSsRes.isEmpty() ? 0 : Integer.parseInt(strSsRes);

        ssA.setText(strSsA);
        ssB.setText(strSsB);
        ssRes.setText(strSsRes);
        A.setText(strA);
        B.setText(strB);
        sign.setText(strSign);
    }

    public void reconstruct() {
        widthA = lpA.leftMargin + A.getWidth() + ssA.getWidth() + lpSign.getMarginStart() + sign.getWidth() + lpB.getMarginStart();
        widthB = B.getWidth() + ssB.getWidth() + lpTextEqual.getMarginStart();
        widthC = textEqual.getWidth() + widthSym * total.getText().length() + ssRes.getWidth() + lpTotal.getMarginStart();
        widthExpression = widthA + widthB + widthC;
        if (widthExpression >= widthLayout) {
            if ((widthB + widthA) < widthLayout) {
                B.setLayoutParams(lpB);
                textEqual.setLayoutParams(lpTextEqual2);
            } else if ((widthB + widthA) >= widthLayout && (widthB + widthC) < widthLayout) {
                B.setLayoutParams(lpB2);
                textEqual.setLayoutParams(lpTextEqual);
            } else if ((widthB + widthC) >= widthLayout) {
                B.setLayoutParams(lpB2);
                textEqual.setLayoutParams(lpTextEqual3);
            }
        } else {
            textEqual.setLayoutParams(lpTextEqual);
            B.setLayoutParams(lpB);
        }
    }
}
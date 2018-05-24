package com.xlg.library.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @Author: Jason
 * @Time: 2018/4/19 10:48
 * @Description:手机号工具类(157 3793 6517)
 */
public class PhoneRegularUtil {

    public PhoneRegularUtil() {
    }

    public static void setTextChange(final EditText phoneEditText) {

        phoneEditText.addTextChangedListener(new TextWatcher() {

            private String oldData = "";
            int selectionIdx;
            boolean isChanged = false;

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            private boolean isDelete(String currentData, String oldData) {
                return currentData.length() < oldData.length();
            }

            private boolean isInput(String currentData, String oldData) {
                return currentData.length() > oldData.length();
            }

            public void afterTextChanged(Editable s) {
                String contents = s.toString();
                String contents2 = contents.replaceAll(" ", "");
                String formatText = contents2.replaceAll("(.{3})(.{0,4})(.{0,4})", "$1 $2 $3").trim();
                if(this.isChanged) {
                    if(this.isDelete(contents, this.oldData)) {
                        phoneEditText.setSelection(this.selectionIdx - 1);
                    } else if(this.isInput(contents, this.oldData)) {
                        phoneEditText.setSelection(this.selectionIdx + 1);
                    } else {
                        phoneEditText.setSelection(this.selectionIdx);
                    }
                }

                this.oldData = contents;
                this.selectionIdx = phoneEditText.getSelectionStart();
                if(formatText.equals(contents)) {
                    this.isChanged = false;
                } else {
                    this.isChanged = true;
                    phoneEditText.setText(formatText);
                }
            }
        });
    }
}

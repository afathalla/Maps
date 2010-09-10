/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.ajaxloader.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayBoolean;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;

/**
 * Convenience methods for working with GWT JsArrays.
 */
public class ArrayHelper {
  public static JsArrayBoolean createJsArray(boolean... bits) {
    JsArrayBoolean result = JsArrayBoolean.createArray().cast();
    for (int i = 0; i < bits.length; i++) {
      result.set(i, bits[i]);
    }
    nativePatchConstructorForSafari(result);
    return result;
  }

  public static JsArrayNumber createJsArray(double... numbers) {
    JsArrayNumber result = JsArrayNumber.createArray().cast();
    for (int i = 0; i < numbers.length; i++) {
      result.set(i, numbers[i]);
    }
    nativePatchConstructorForSafari(result);
    return result;
  }

  public static JsArrayInteger createJsArray(int[] integers) {
    JsArrayInteger result = JsArrayInteger.createArray().cast();
    for (int i = 0; i < integers.length; i++) {
      result.set(i, integers[i]);
    }
    nativePatchConstructorForSafari(result);
    return result;
  }

  public static JsArrayString createJsArray(String... strings) {
    JsArrayString result = JsArrayString.createArray().cast();
    for (int i = 0; i < strings.length; i++) {
      result.set(i, strings[i]);
    }
    nativePatchConstructorForSafari(result);
    return result;
  }

  public static <J extends JavaScriptObject> JsArray<J> toJsArray(J... objects) {
    JsArray<J> result = JsArray.createArray().cast();
    for (int i = 0; i < objects.length; i++) {
      result.set(i, objects[i]);
    }
    nativePatchConstructorForSafari(result);
    return result;
  }

  /**
   * This is a fixup for the constructor used to address issue 219 - Safari bug
   * in comparing Date & Array constructors.
   * 
   * @param result
   */
  private static native void nativePatchConstructorForSafari(JavaScriptObject result) /*-{
    result.constructor = $wnd.Array;
  }-*/;

}

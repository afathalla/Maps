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
package com.google.gwt.maps.jsio.client;

import com.google.gwt.maps.jsio.client.impl.MetaDataName;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * This annotation is used on a JSWrapper class in a manner similar to
 * {@link Constructor} although the value is interpreted as a value reference
 * rather than a function.
 */
@Documented
@MetaDataName("gwt.global")
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface Global {
  String value();
}

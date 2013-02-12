/*
 * Copyright (c) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.client.http;

import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.util.ArrayMap;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests {@link UrlEncodedContent}.
 *
 * @author Yaniv Inbar
 */
public class UrlEncodedContentTest extends TestCase {

  public UrlEncodedContentTest() {
  }

  public UrlEncodedContentTest(String name) {
    super(name);
  }

  public void testWriteTo() throws IOException {
    assertEquals("a=x", toString(ArrayMap.of("a", "x")));
    assertEquals("noval", toString(ArrayMap.of("noval", "")));
    assertEquals(
        "multi=a&multi=b&multi=c", toString(ArrayMap.of("multi", Arrays.asList("a", "b", "c"))));
    assertEquals(
        "multi=a&multi=b&multi=c", toString(ArrayMap.of("multi", new String[] {"a", "b", "c"})));
  }

  static String toString(Object data) throws IOException {
    UrlEncodedContent content = new UrlEncodedContent(data);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    content.writeTo(out);
    return out.toString();
  }

  public void testGetContent() throws Exception {
    HttpRequest request = new MockHttpTransport().createRequestFactory()
        .buildGetRequest(HttpTesting.SIMPLE_GENERIC_URL);
    UrlEncodedContent content = UrlEncodedContent.getContent(request);
    assertNotNull(content);
    assertTrue(content.getData() instanceof Map);
    assertEquals(content, UrlEncodedContent.getContent(request));
  }

  public void testGetData() {
    try {
      new UrlEncodedContent(null);
      fail("expected " + NullPointerException.class);
    } catch (NullPointerException e) {
      // expected
    }
    Map<String, Object> map = new HashMap<String, Object>();
    UrlEncodedContent content = new UrlEncodedContent(map);
    assertEquals(map, content.getData());
  }
}

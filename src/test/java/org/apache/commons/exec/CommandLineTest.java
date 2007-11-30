/* 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.apache.commons.exec;

import java.util.Arrays;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class CommandLineTest extends TestCase {

    private void assertEquals(String[] expected, String[] actual) {
        if (!Arrays.equals(expected, actual)) {
            throw new AssertionFailedError("Arrays not equal");
        }
    }

    public void testExecutable() {
        CommandLine cmdl = new CommandLine("test");
        assertEquals("test", cmdl.toString());
        assertEquals(new String[] {"test"}, cmdl.toStrings());
        assertEquals("test", cmdl.getExecutable());
        assertTrue(cmdl.getArguments().length == 0);
    }

    public void testExecutableZeroLengthString() {
        try {
            CommandLine cmdl = new CommandLine("");
            fail("Must throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    public void testExecutableWhitespaceString() {
        try {
            CommandLine cmdl = new CommandLine("   ");
            fail("Must throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    public void testNullExecutable() {
        try {
            CommandLine cmdl = new CommandLine((String)null);
            fail("Must throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    public void testAddArgument() {
        CommandLine cmdl = new CommandLine("test");

        cmdl.addArgument("foo");
        cmdl.addArgument("bar");
        assertEquals("test foo bar", cmdl.toString());
        assertEquals(new String[] {"test", "foo", "bar"}, cmdl.toStrings());
    }

    public void testAddNullArgument() {
        CommandLine cmdl = new CommandLine("test");

        cmdl.addArgument(null);
        assertEquals("test", cmdl.toString());
        assertEquals(new String[] {"test"}, cmdl.toStrings());
    }

    public void testAddArgumentWithSpace() {
        CommandLine cmdl = new CommandLine("test");
        cmdl.addArgument("foo");
        cmdl.addArgument("ba r");
        assertEquals("test foo \"ba r\"", cmdl.toString());
        assertEquals(new String[] {"test", "foo", "\"ba r\""}, cmdl.toStrings());
    }

    public void testAddArgumentWithQuote() {
        CommandLine cmdl = new CommandLine("test");
        cmdl.addArgument("foo");
        cmdl.addArgument("ba\"r");
        assertEquals("test foo 'ba\"r'", cmdl.toString());
        assertEquals(new String[] {"test", "foo", "'ba\"r'"}, cmdl.toStrings());
    }

    public void testAddArgumentWithQuotesAround() {
        CommandLine cmdl = new CommandLine("test");
        cmdl.addArgument("\'foo\'");
        cmdl.addArgument("\"bar\"");
        cmdl.addArgument("\"fe z\"");
        assertEquals("test foo bar \"fe z\"", cmdl.toString());
        assertEquals(new String[] {"test", "foo", "bar", "\"fe z\""}, cmdl.toStrings());
    }

    public void testAddArgumentWithSingleQuote() {
        CommandLine cmdl = new CommandLine("test");

        cmdl.addArgument("foo");
        cmdl.addArgument("ba'r");
        assertEquals("test foo \"ba'r\"", cmdl.toString());
        assertEquals(new String[] {"test", "foo", "\"ba\'r\""}, cmdl
                .toStrings());
    }

    public void testAddArgumentWithBothQuotes() {
        CommandLine cmdl = new CommandLine("test");

        try {
            cmdl.addArgument("b\"a'r");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // OK, expected
        }
    }

    public void testAddArguments() {
        CommandLine cmdl = new CommandLine("test");
        cmdl.addArguments("foo bar");
        assertEquals("test foo bar", cmdl.toString());
        assertEquals(new String[] {"test", "foo", "bar"}, cmdl.toStrings());
    }

    public void testAddArgumentsWithQuotes() {
        CommandLine cmdl = new CommandLine("test");
        cmdl.addArguments("'foo' \"bar\"");
        assertEquals("test foo bar", cmdl.toString());
        assertEquals(new String[] {"test", "foo", "bar"}, cmdl.toStrings());
    }

    public void testAddArgumentsWithQuotesAndSpaces() {
        CommandLine cmdl = new CommandLine("test");
        cmdl.addArguments("'fo o' \"ba r\"");
        assertEquals("test \"fo o\" \"ba r\"", cmdl.toString());
        assertEquals(new String[] {"test", "\"fo o\"", "\"ba r\""}, cmdl
                .toStrings());
    }

    public void testAddArgumentsArray() {
        CommandLine cmdl = new CommandLine("test");
        cmdl.addArguments(new String[] {"foo", "bar"});
        assertEquals("test foo bar", cmdl.toString());
        assertEquals(new String[] {"test", "foo", "bar"}, cmdl.toStrings());
    }

    public void testAddArgumentsArrayNull() {
        CommandLine cmdl = new CommandLine("test");
        cmdl.addArguments((String[]) null);
        assertEquals("test", cmdl.toString());
        assertEquals(new String[] {"test"}, cmdl.toStrings());
    }

    public void testParse() {
        CommandLine cmdl = CommandLine.parse("test foo bar");
        assertEquals("test foo bar", cmdl.toString());
        assertEquals(new String[] {"test", "foo", "bar"}, cmdl.toStrings());
    }

    public void testParseWithQuotes() {
        CommandLine cmdl = CommandLine.parse("test \"foo\" \'ba r\'");
        assertEquals("test foo \"ba r\"", cmdl.toString());
        assertEquals(new String[] {"test", "foo", "\"ba r\""}, cmdl.toStrings());
    }

    public void testParseWithUnevenQuotes() {
        try {
            CommandLine.parse("test \"foo bar");
            fail("IllegalArgumentException must be thrown due to uneven quotes");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    public void testParseWithNull() {
        try {
            CommandLine.parse(null);
            fail("IllegalArgumentException must be thrown due to incorrect command line");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    public void testParseWithOnlyWhitespace() {
        try {
            CommandLine.parse("  ");
            fail("IllegalArgumentException must be thrown due to incorrect command line");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
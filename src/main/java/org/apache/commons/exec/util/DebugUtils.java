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

package org.apache.commons.exec.util;

/**
 * Helper classes to provide debugging support.
 *
 * @author <a href="mailto:siegfried.goeschl@it20one.at">Siegfried Goeschl</a>
 */
public class DebugUtils
{
    /**
     * System property to determine how to handle exceptions. When
     * set to "false" we rethrow the otherwise silently catched
     * exceptions found in the original code. The default value
     * is "true"
     */
    public static final String COMMONS_EXEC_LENIENT = "COMMONS_EXEC_LENIENT";

    /**
     * System property to determine how to dump an exception. When
     * set to "true" we print any exception to stderr. The default
     * value is "false"
     */
    public static final String COMMONS_EXEC_DEBUG = "COMMONS_EXEC_DEBUG";

    /**
     * Handle an exception based on the system properties.
     *
     * @param e an exception being handled
     */
    public static void handleException(String msg, Exception e) {

        if(isDebugEnabled()) {
            System.err.println(msg);
            e.printStackTrace();
        }

        if(!isLenientEnabled()) {
            if(e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            else {
                // can't pass root cause since the constructor is not available on JDK 1.3
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    /**
     * Determine if debugging is enabled based on the
     * system property "COMMONS_EXEC_DEBUG".
     *
     * @return true if debug mode is enabled
     */
    public static boolean isDebugEnabled() {
        return "true".equalsIgnoreCase(System.getProperty("COMMONS_EXEC_DEBUG", "false"));
    }

    /**
     * Determine if lenient mode is enabled.
     *
     * @return true if lenient mode is enabled
     */
    public static boolean isLenientEnabled() {
        return "true".equalsIgnoreCase(System.getProperty("COMMONS_EXEC_LENIENT", "true"));
    }

}
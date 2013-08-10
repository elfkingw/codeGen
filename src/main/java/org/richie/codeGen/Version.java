/*
 * Copyright 2013 elfkingw
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Created on 2013-8-10

package org.richie.codeGen;

/**
 * @author elfkingw
 */
public class Version {

    public final static int MajorVersion    = 0;
    public final static int MinorVersion    = 1;
    public final static int RevisionVersion = 0;

    public static String getVersionNumber() {
        return Version.MajorVersion + "." + Version.MinorVersion + "." + Version.RevisionVersion;
    }
}

/**
 * Copyright (C) 2012 Red Hat, Inc. (jcasey@redhat.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.commonjava.maven.ext.manip.state;

import java.util.Map;
import java.util.Properties;

import org.commonjava.maven.ext.manip.impl.ProjectVersioningManipulator;

/**
 * Captures configuration and changes relating to the projects' versions. Used by {@link ProjectVersioningManipulator}.
 *
 * @author jdcasey
 */
public class VersioningState
    implements State
{

    public static final String VERSION_SUFFIX_SYSPROP = "version.suffix";

    public static final String INCREMENT_SERIAL_SUFFIX_SYSPROP = "version.incremental.suffix";

    public static final String VERSION_SUFFIX_SNAPSHOT_SYSPROP = "version.suffix.snapshot";

    public static final String VERSION_OSGI_SYSPROP = "version.osgi";

    public static final String VERSION_OVERRIDE_SYSPROP = "version.override";

    private Map<String, String> versioningChanges;

    private final String suffix;

    private final String incrementSerialSuffix;

    private final boolean preserveSnapshot;

    private final boolean osgi;

    private final String override;

    public VersioningState( final Properties userProps )
    {
        suffix = userProps.getProperty( VERSION_SUFFIX_SYSPROP );
        incrementSerialSuffix = userProps.getProperty( INCREMENT_SERIAL_SUFFIX_SYSPROP );
        preserveSnapshot = Boolean.parseBoolean( userProps.getProperty( VERSION_SUFFIX_SNAPSHOT_SYSPROP ) );
        osgi = Boolean.parseBoolean( userProps.getProperty( VERSION_OSGI_SYSPROP, "true" ) );
        override = userProps.getProperty( VERSION_OVERRIDE_SYSPROP );
    }

    public void setVersioningChanges( final Map<String, String> versioningChanges )
    {
        this.versioningChanges = versioningChanges;
    }

    public Map<String, String> getVersioningChanges()
    {
        return versioningChanges;
    }

    public String getIncrementalSerialSuffix()
    {
        return incrementSerialSuffix;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public boolean preserveSnapshot()
    {
        return preserveSnapshot;
    }

    public String getOverride()
    {
        return override;
    }

    /**
     * True if we should make the versions OSGi compliant
     * @return
     */
    public boolean osgi()
    {
        return osgi;
    }

    /**
     * Enabled ONLY if either version.incremental.suffix or version.suffix is provided in the user properties / CLI -D options.
     *
     * @see #VERSION_SUFFIX_SYSPROP
     * @see #INCREMENT_SERIAL_SUFFIX_SYSPROP
     * @see org.commonjava.maven.ext.manip.state.State#isEnabled()
     */
    @Override
    public boolean isEnabled()
    {
        return incrementSerialSuffix != null || suffix != null || override != null;
    }

}

CASALite
========

<p>CASALite is a Java framework to develop communicative agent-based robots using SunSPOT and iRobot Create.</p>
<h3>CASALite</h3>
<p>The repository "CASALite" is structured as an Eclipse project. 
Because of cross-platform issues, we do not include the Eclipse file ".classpath", which is located in the root folder of every Eclipse project. 
In our case, the contents of this file are given below, where references to "SUNSPOT_HOME" must be replaced by the local folder where the SunSPOT SDK is installed.
This file should be recreated manually after downloading "CASALite".
</p>
-------
<pre>
&lt;?xml version="1.0" encoding="UTF-8"?>
&lt;classpath>
    &lt;classpathentry kind="src" path="src"/>
    &lt;classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/CDC-1.0%Foundation-1.0"/>
    &lt;classpathentry kind="lib" path="/SUNSPOT_HOME/sdk/lib/ipv6lib_common.jar"/>
    &lt;classpathentry kind="lib" path="/SUNSPOT_HOME/sdk/lib/multihop_common.jar"/>
    &lt;classpathentry kind="lib" path="/SUNSPOT_HOME/sdk/lib/squawk_common.jar"/>
    &lt;classpathentry kind="lib" path="/SUNSPOT_HOME/sdk/lib/create_host.jar"/>
    &lt;classpathentry kind="lib" path="/SUNSPOT_HOME/sdk/lib/sdk/lib/spotlib_device.jar"/>
    &lt;classpathentry kind="lib" path="/SUNSPOT_HOME/sdk/lib/sdk/lib/transducer_device.jar"/>
    &lt;classpathentry kind="lib" path="/SUNSPOT_HOME/sdk/lib/sdk/lib/spotlib_common.jar"/>
    &lt;classpathentry kind="output" path="bin"/>
&lt;/classpath>
</pre>
--------

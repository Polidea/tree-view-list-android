----------------------------------------------------------------------------
Requirements :
	- Android SDK - https://dl-ssl.google.com/android/eclipse/
	- Ant ( only if not using IDE).

Additonal requirements for IDE :

- Eclipse Helios 3.6 IDE - http://www.eclipse.org/downloads/
- Android Eclipse ADT Plugin - http://developer.android.com/sdk/index.html

Optional requirements ( for analysis ) :
- PMD - http://pmd.sourceforge.net/
- Checkstyle - http://checkstyle.sourceforge.net/
- Findbugs - http://findbugs.sourceforge.net/
----------------------------------------------------------------------------

Building :

I. Building from ANT

	1. Download the dependent libraries into directory containg the project (you can also run ant -f build_android_manage_template.xml checkout_libraries)
	2. Copy ant.properties from template to local.properties in main directory, and edit appropriate paths ( sdk.dir is required )
	3. Run build task (default) from build.xml

II. Building from Eclipse

	1. Download the dependent libraries (you can also run ant -f build_android_manage_template.xml checkout_libraries)
	2. Add ant.properties to "global property files in eclipse" or copy it to local.properties, edit appropriate paths ( sdk.dir is required )
	3. Install Eclipse IDE, and ADT Android plugin (http://developer.android.com/sdk/eclipse-adt.html#installing)
	4. Configure Android SDK in Eclipse IDE
	5. Download SDK Platform 8 in Android SDK Manager (Eclipse->Window->Android SDK and AVD manager)
	6. Import dependent libraries into eclipse workspace
	7. Run application.


----------------------------------------------------------------------------
Publishing :
----------------------------------------------------------------------------

To publish application on Android Market use instructions described here :

1. Sign application
http://developer.android.com/guide/publishing/app-signing.html

2. Publish
http://developer.android.com/guide/publishing/publishing.html

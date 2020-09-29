# ProgressStepLine
Installation gradle: 

Step 1. Add it in your root build.gradle at the end of repositories:

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  Step 2. Add the dependency

  	dependencies {
	        implementation 'com.github.artem-nr:ProgressStepLine:Tag'
	}
  
  Installation maven:
  
Step 1. Add the JitPack repository to your build file

  <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
  
  Step 2. Add the dependency
  
  <dependency>
	    <groupId>com.github.artem-nr</groupId>
	    <artifactId>ProgressStepLine</artifactId>
	    <version>Tag</version>
	</dependency>

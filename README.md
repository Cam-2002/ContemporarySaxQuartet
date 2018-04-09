Contemporary Sax Quartet Generator
==================================

## About

This is a simple java program that generates saxophone quartet music, with random generators. It outputs a midi file in the folder that contains the .JAR file. It will also give the file a random title.

## Downloads

[Download JAR](https://github.com/Cam-2002/ContemporarySaxQuartet/raw/master/ContemporarySaxQuartet.jar)  
[Download Source](https://github.com/Cam-2002/ContemporarySaxQuartet/raw/master/ContemporarySaxQuartet-Src.zip)

## Installing

**Note:** This requires Java 7.

There is nothing to install, just download and run the ContemporarySaxQuartet.jar file. This can either be done by double clicking in windows, or through the command line with "java -jar ContemporarySaxQuartet.jar". There are no flags.

## Settings

When generating the midi file, there are settings to adjust what is output. This is the list, and what each option does:

  * *Range*: This defines the range of each instrument. Normal is the standard range for each saxophone, Bb -> F. Altissimo adds one octave to that range. Insane allows for two octaves above the normal range, as well as an octave below.
  * *Rhythms*: This defines what notes can be written. Normal is anywhere from a whole note to a sixteenth note. Faster is anywhere from a whole note to a thirty-second note. Insane is anywhere from a double whole note to a hundred twenty-eighth note.
  * *Follow Scale*: This will either make the random notes stick to a randomly generated key (Ex. C Major, F# Major, B Minor) or be entirely chromatic.
  * *Quarter Notes*: This determines the length of the piece, in quarter notes.
  * *Saxophones*: Here you can select what saxophones are in the "quartet".
  * *Generate*: This generates the midi file with the above settings.

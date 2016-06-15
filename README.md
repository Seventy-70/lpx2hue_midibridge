# lpx2hue_midibridge
Connect Logic Pro X recording light to Philips Hue system


Validated on Mac OSX 10.11.5 El Capitan, and with Logic Pro X 10.2.3

# Setup steps on Mac OSX


## 1) Add Virtual IAC Midi Port to system 
- Start "Audio MIDI Setup"
- Open "Window -> Show MIDI Studio" (Cmd-2)
- You will now see an icon for the IAC Driver. If you have never used the IAC Driver before, the icon will appear grayed out. 
- Double-click the IAC Driver icon to bring up its properties dialog. 
- In the properties dialog, click the “Device is online” checkbox to make the driver active. In the Audio MIDI setup window, the icon will no longer appear grayed out.
- The bottom part of the IAC dialog (you may have to click the arrow next to “More information” to get to see this), you find an overview with driver ports.
- Add a port (click the + symbol)
- Name the port "LPX2HUE_MB"

## 2) Setup recording light control interface on Logic Pro X
- 


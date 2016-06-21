# lpx2hue_midibridge
Connect Logic Pro X recording light to Philips Hue system


Validated on Mac OSX 10.11.5 El Capitan, and with Logic Pro X 10.2.3, and Philips HUE Bridge Rest API v1.12

# Setup steps on Mac OSX


## 1) Add Virtual IAC Midi Port to system 
- Start 'Audio MIDI Setup' from Applications
- Open 'Window -> Show MIDI Studio' (Cmd-2)
- You will now see an icon for the IAC Driver. If you have never used the IAC Driver before, the icon will appear grayed out. 
- Double-click the IAC Driver icon to bring up its properties dialog. 
- In the properties dialog, click the “Device is online” checkbox to make the driver active. In the Audio MIDI setup window, the icon will no longer appear grayed out.
- The bottom part of the IAC dialog (you may have to click the arrow next to “More information” to get to see this), you find an overview with driver ports.
- Add a port (click the + symbol)
- Name the port "LPX2HUE_MB"

## 2) Setup Recording Light control surface on Logic Pro X
- In Preferences, enable Advanced Tools. "Open Logic Pro X -> Preferences -> Advanced Tab -> select 'Show Advanced Tools'"
- Make sure the checkboxes for 'Midi' and 'Control Surfaces' are enabled
- Open control surfaces. Open 'Logic Pro X -> Control Surfaces - Setup...'
- In the top menu of the dialog select 'New -> Install...'
- From the 'install' dialog select the 'Recording Light'
- On the 'Control Surface Setup' window, select the 'Recording Light' icon
- The left side of the dialog shows the setup properties
- In the output port select 'LPX2HUE_MB' which has been create in step 1

## 3) Configure the lpx2hue_midibridge component
- run the program, which lists the input devices, select the input with name: LPX2HUE_MB
- ... more steps to follow
  - endpoint of HUE bridge
  - name of IAC port
  - specify HUE user
  - define control lights / groups





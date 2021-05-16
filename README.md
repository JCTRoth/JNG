# JNG
Clone of the game Jets'n'Guns for up to 3 local player. The game features 6 different parameterized areas.

## Fetch Code
```
git clone git@github.com:danielroth1/JNG.git
cd JNG
```

## Linux
Make sure to install java and basic rendering components (those should be already installed):
```
sudo apt install openjdk-8-jdk
sudo apt install x11-xserver-utils
sudo apt-get install libopenal-dev
```
and then:
```
./compile.sh
./run.sh
```

## Windows
Make sure to have java installed and javaw and javac in the PATH:
```
set PATH=%PATH%;<path-to-javac.exe-javaw.exe>
```
and then:
```
compile.bat
run.bat
```

## Controls
Fullscreen: F1

Other Controls: See Controls menu in-game

## Features
Play with different level sizes (smaller = more difficult).

The 6 parameterized areas always slightly different.

<img src="https://github.com/danielroth1/JNG/blob/e4553dc51767e501221a0879c2595b24e70d7a70/pictures/level2.png" width="500"/>
<img src="https://github.com/danielroth1/JNG/blob/e4553dc51767e501221a0879c2595b24e70d7a70/pictures/level3.png" width="500"/>

Multiplayer for up to 3 players playing at the same keyboard.

<img src="https://github.com/danielroth1/JNG/blob/e4553dc51767e501221a0879c2595b24e70d7a70/pictures/level1_multiplayer.png" width="500"/>

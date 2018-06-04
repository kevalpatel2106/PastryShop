# PastryShop

[![Build Status](https://travis-ci.com/kevalpatel2106/PastryShop.svg?branch=master)](https://travis-ci.com/kevalpatel2106/PastryShop) [![codecov](https://codecov.io/gh/kevalpatel2106/PastryShop/branch/master/graph/badge.svg)](https://codecov.io/gh/kevalpatel2106/PastryShop) [![API](https://img.shields.io/badge/API-21%2B-orange.svg?style=flat)](https://android-arsenal.com/api?level=21)

#### Demo application for delicious pastries shop.

## Features:
- Beautiful animations.
- Offline first approach. All the network responses are cached into the database (powered by [Room](https://developer.android.com/topic/libraries/architecture/room)).
- Uses android architecture components.
- Follows MVVM architecture pattern.
- Uses dagger heavily to provide clear separation between different architecture layers. Helps to write testable code.
- Mocking database, shared preferences and web server to write accurate tests.
- Single activity application.

## How to run tests?
- Make sure you connect physical device or run an emulator before running the UI tests.
- To run tests, run following command:
```bash
./gradlew cAT
```

## Acknowledgement:
- This application implements the design and animations from the beautiful post on [uplabs](https://www.uplabs.com/posts/restaurant-app-for-android). (There are some changes in resources such as fonts, images and texts from the original mock up.)
- Code coverage currently displays percentage of code tested by the unit tests. (UI tests are not being performed on CI server.)

## Screenshots:

|Portrait (Pixel 2)|Landscape (Nexus 9)|
|:---:|:---:|
|![portrait.gif](/.github/portrait.gif)|![landscape.gif](/.github/landscape.gif)|


## What to try this out?
- You can download the debuggable apk from [here](https://github.com/kevalpatel2106/PastryShop/releases) and play with it.


## Want to contribute?
Every small or large contribution to this project is appreciated. Make sure you read the [contribution guide](/.github/CONTRIBUTING.md) before generating the pull request.

## Questions?🤔
Hit me on twitter [![Twitter](https://img.shields.io/badge/Twitter-@kevalpatel2106-blue.svg?style=flat)](https://twitter.com/kevalpatel2106)

## License
Copyright 2018 Keval Patel

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

<div align="center">
<img src="https://cloud.githubusercontent.com/assets/370176/26526332/03bb8ac2-432c-11e7-89aa-da3cd1c0e9cb.png">
</div>

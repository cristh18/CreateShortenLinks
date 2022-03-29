# CreateShortenLinks

The developed application allows to build out a single page mobile app for URL shortening

## Code

https://github.com/cristh18/CreateShortenLinks

## Architecture
I have designed an architectural style based on clean architecture (https://github.com/android10/Android-CleanArchitecture) and made use of the MVVM pattern with which we could divide the whole system into layers, making each of them have a higher degree of independence and a specific task of the business process. Other advantages of this style are:

- Increased reusability of components.
- Increased modifiability.
- Better defined processes.
- Standardization.
- Easy mitigation.
- Increased scalability.
- System modularization.

https://developer.android.com/jetpack/guide#samples

Reactive scheduling is used to take advantage of its benefits and to notify and update the status of the application immediately without the need to launch a manual process. For this purpose, the advantages of coroutines and flow are taken advantage of.

https://developer.android.com/kotlin/flow

## Demo

![Alt Text](https://media.giphy.com/media/62OQ7NyRq0JOdSRyFF/giphy.gif)

![Alt Text](https://media.giphy.com/media/rv4Kv4Am4zGFv5XMrQ/giphy.gif)

## Instrumentation Tests

![Alt Text](https://media.giphy.com/media/Pzjliyg3pEniUQWV0V/giphy.gif)




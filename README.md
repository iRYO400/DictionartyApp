# DictionartyApp
The test project for job apply. Developed using Kotlin 1.9.10 and Android Studio Giraffe

## Comments
Let's talk about `Develop an ingestion process, filling the local data base from the dataset.`. At the beginning I had two ideas in my head. 
1. The first idea, I call it `lazy`, was to convert JSON's content to database tables with all the needed relations and ship it with an app in the `Assets` or `Raw` folders. Then open these tables using [Room's](https://developer.android.com/training/data-storage/room) `createFromFile`/`createFromAsset` and start using it as regular database.
2. The second is what I've got in the repository. Convert JSON's content to Kotlin data classes using [Moshi](https://github.com/square/moshi) and insert it into Room.

## TODOs
There are todos inside the project, for example:
- Show suggestions when typing in SearchBar;
- Improve UI according to some UX( steal ideas from Dribbble, Pinterest, etc.);
- Clickable synonyms for faster navigation

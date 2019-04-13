# Movielicious

It's an App that shows you the most popular movies in this instant , it's build using Model-View-ViewModel Concepts(MVVM) , Dependency Injections such as Dagger 2(DI) , Lifecycles , Room(Database Persistance Library) , Databinding , RxJava 2 (Networking) , Mockito (For Unit Testing) 


![alt text](https://raw.githubusercontent.com/HossamOnsy/Movielicious/master/scs/0.jpg)![alt text](https://raw.githubusercontent.com/HossamOnsy/Movielicious/master/scs/2.jpg)![alt text](https://raw.githubusercontent.com/HossamOnsy/Movielicious/master/scs/3.jpg)![alt text](https://raw.githubusercontent.com/HossamOnsy/Movielicious/master/scs/4.jpg)



## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

```
Very Important to Add your API Key in utils/Constants.kt in API_KEY variable 
```

### Knowledge with :

```
Android Studio
Familiar with Kotlin
Familiar with Dependency Injections
Familiar with SQLite Databases
Familiar with Databinding
Familiar with Networking
```

## Development process
<br>
1. API Service -> API Service Unit Test with api response mock files (checking connection) <br>
2. DAO -> DAO Unit Test <br>
3. ViewModel -> ViewModel Unit Test <br>
4. DI & Refactoring <br>
5. Implmentating UI & Layouts <br>
<br>

## Tests

There are 3 Tests available 
you can run them by right clicking on the test and then press run

1 is under the androidTest folder and this test will test the database entry of your favorite movie and then making sure it's inside the database

```
 val movieDetailModel = MovieDetailModel()
        movieDetailModel.id = 1234321
        movieDetailModel.favorite = true

        val datamodel = database.movieDao().getModel(movieDetailModel.id, movieDetailModel.favorite)
        assertEquals(datamodel.id, 1234321)

```
2,3 are under the test folder as they are testing the call of the apis and making sure they are being called 


### What did I use in testing ? 

I've used 2 ways :

1. Mockito as it's easy to use it for interactions and making sure method is called as required

```
 @Mock
    var restApi: RestApi? = null
```

2. AndroidJUnit Instrumental  and the reason is the database as it's important to test it on your device for caching 

```
@RunWith(AndroidJUnit4::class)

  private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            AppDatabase::class.java
        ).build()
    }
```
### Why did I use Room ? 

1.When you press on a movie item , you will enter into MovieDetailsScreen.
2.Then it will check if the element is in your favorites , if yes it will load it from the database, otherwise it will call the api to get your movie details from it 
3.if it's not in your favorite , You will be able to favorite that item and once you do that , this item is set into the database as one of your favorite movies
4.when you enter to that movie details again step 2 will apply and hence you will see the favorite button lit as indication that you have this element marked as one of your favorite movies 

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management




## Specs & Open-source libraries
- 100% Kotlin based
- MVVM Architecture
- Architecture Components (Lifecycle, LiveData, ViewModel, Room Persistence)
- Material Design & Animations
- The Movie DB API
- [Dagger2](https://github.com/google/dagger) for dependency injection
- [Retrofit2 & Gson](https://github.com/square/retrofit) for constructing the REST API
- [OkHttp3 logging interceptor](https://github.com/square/okhttp) for implementing interceptor, logging 
- [Glide](https://github.com/bumptech/glide) for loading images
- [Mockito-inline](https://github.com/nhaarman/mockito-kotlin) for Junit mock test
- [airbnb/lottie](https://github.com/airbnb/lottie-android) for Animation
- [Animatoo](https://github.com/mohammadatif/Animatoo) for Animation
- [SpotsDialogue](https://github.com/d-max/spots-dialog) for Animation



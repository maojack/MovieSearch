# Welcome to MovieSearch

## Requirements
- Minimum Android API level 24 (Nougat)

## Features
This is a personal Android practice project utilizing the [OMDB API](https://www.omdbapi.com/).
- The project uses [Retrofit2](https://square.github.io/retrofit/) library to establish REST HTTP call. a
- Results are presented using CardView within RecyclerView
- Clicking on a CardView item currently will establish another REST call to fetch the Movie's plot
- The plot section is then expended from the existing CardView
- Scrolling to the end of the RecyclerView will again make another REST call to fetch the next page of search result (if there is any).

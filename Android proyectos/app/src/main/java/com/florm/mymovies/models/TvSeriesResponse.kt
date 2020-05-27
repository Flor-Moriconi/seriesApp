package com.florm.mymovies.models

data class TvSeriesResponse(val page: Int, val results: List<TvSerie>, var total_results: Int, var total_pages: Int)
package com.jessy.foodmap.data

import android.media.Image

class Data {

    //建立會員
    data class Member (
        var userName:String,
        var age:Int,
        var gender: String,
        var email: String,
        var password: String
    )

    //加入行程
    data class Journey (
        var image: Image,
        var journeyName:String,
        var returnDate: Data

    )

    //加入景點
    data class Place (
        var journeyName:String,
        var anyDay:String,
        var transportation: String,
        var dwellTime: String

        )

    //邀請好友
//    data class AddFriend (
//
//            )

    //發文
//    data class article (
//        var image: Image,
//        var author:String,
//        var collectNumber: String
//            )

    //我的迷霧美食地圖
//    data class MyFoodMap(
//        var Place:String
//    )

}


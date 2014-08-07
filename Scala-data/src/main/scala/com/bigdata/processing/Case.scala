/**
 * @author   JohnLiu  
 * @date     2014/8/6
 * @version  0.1.0
 */
package com.bigdata.processing ;

case class Artist(name: String, genre: String)
case class ArtistWithAlbums(artist: Artist, albums: List[String])





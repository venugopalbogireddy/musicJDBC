package com.venugopalb;


import com.venugopalb.model.Artist;
import com.venugopalb.model.Datasource;
import com.venugopalb.model.SongArtist;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Datasource datasource = new Datasource();
        if (!datasource.open()) {
            System.out.println("Cant open datasource");
            return;
        }

        List<Artist> artists = datasource.queryArtists(5);
        if(artists == null) {
            System.out.println("No artists!");
            return;
        }

        for(Artist artist : artists) {
            System.out.println("ID = " + artist.getId() + ", Name = " + artist.getName());
        }

        System.out.println("----------------------------------");

        List<String> albumsForArtist =
                datasource.queryAlbumsForArtist("Carole King", Datasource.ORDER_BY_ASC);

        for(String album : albumsForArtist) {
            System.out.println(album);
        }

        System.out.println("----------------------------------");

        List<SongArtist> songArtists = datasource.queryArtistsForSong("Go Your Own Way", Datasource.ORDER_BY_ASC);
        if(songArtists == null) {
            System.out.println("Couldn't find the artist for the song");
            return;
        }

        for(SongArtist artist : songArtists) {
            System.out.println("Artist name = " + artist.getArtistName() +
                    " Album name = " + artist.getAlbumName() +
                    " Track = " + artist.getTrack());
        }

        System.out.println("----------------------------------");
        System.out.print("Schema : ");
        datasource.querySongsMetadata();

        System.out.println("----------------------------------");
        int count = datasource.getCount(Datasource.TABLE_SONGS);
        System.out.println("Number of Songs is " + count);

        datasource.createViewForSongArtists();

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter a song title: ");
//        String title = scanner.nextLine();

        // Go Your Own Way" or 1=1 or "
        // SQL Injection attack

        // nothing inside the given title is treated as sql

//        songArtists = datasource.querySongInfoView(title);
//        if(songArtists.isEmpty()) {
//            System.out.println("Couldn't find the artist for the song");
//            return;
//        }

        // SELECT name, album, track FROM artist_list WHERE title = "Go Your Own Way" or 1=1 or ""

        // SELECT name, album, track FROM artist_list WHERE title = "Go Your Own Way or 1=1 or ""

        // SELECT name, album, track FROM artist_list WHERE title = ? OR artist = ?

        for(SongArtist artist : songArtists) {
            System.out.println("FROM VIEW - Artist name = " + artist.getArtistName() +
                    " Album name = " + artist.getAlbumName() +
                    " Track number = " + artist.getTrack());
        }

        datasource.insertSong("Like a Rolling Stone", "Bob Dylan", "Bob Dylan's Greatest Hits", 5);
        datasource.insertSong("Bird Dog", "Everly Brothers", "Hits", 7);

        datasource.close();
    }
}

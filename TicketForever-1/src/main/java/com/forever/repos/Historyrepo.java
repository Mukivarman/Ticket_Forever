package com.forever.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.forever.dbentity.History;

import java.util.List;

import java.util.Date;


public interface Historyrepo extends JpaRepository<History,Long>{
		
	@Query(value = "SELECT h.Seatnum FROM History h WHERE h.moviename = ?1"
			+ " AND h.showdate = ?2 AND h.showtime = ?3", nativeQuery = true)
	List<Integer> findSeatnumByMovieNameAndDateTime(
	    String movieName,
	    Date date,
	    String time
	);
	@Query(value = "Select * FROM History h  WHERE h.users_userid = ?1 order by bookingdate desc,bookingtime desc",
			nativeQuery = true)
	List<History> findAllById(Long id);

	@Query(value="select * from history inner join userdb on history.users_userid=userdb.userid order by bookingdate,bookingtime desc",nativeQuery = true)
		List<History> findAllHistory();
}

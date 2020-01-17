package org.cerner.multiplex.hospitalinfo.resource;


import java.util.List;

import org.cerner.multiplex.hospitalinfo.dao.HospitalDetailsDAO;
import org.cerner.multiplex.hospitalinfo.model.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HospitalResource {

    @Autowired
    private HospitalDetailsDAO<Integer, Hospital> hospitalDAO;

    @PostMapping("/hospital")
    public ResponseEntity<List<Hospital>> add(@RequestBody List<Hospital> hospitals)
    {
        for(Hospital hospital : hospitals)
            hospitalDAO.add(hospital.getId(), hospital);
        return new ResponseEntity<>(hospitals,HttpStatus.OK);
    }

    @GetMapping("/hospital/{id}")
    ResponseEntity<Hospital> get(@PathVariable("id") String id)
    {
        int key = Integer.parseInt(id);
        return ResponseEntity.status(HttpStatus.OK).body(hospitalDAO.get(key));
    }

    @PutMapping("/hospital")
    public ResponseEntity<Hospital> update(@RequestBody Hospital hospital)
    {
        hospitalDAO.addOrUpdate(hospital.getId(), hospital);
        return new ResponseEntity<>(hospital, HttpStatus.OK);
    }

    @DeleteMapping(value = "/hospital/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        int key = Integer.parseInt(id);
        boolean isRemoved = hospitalDAO.remove(key);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /* @GetMapping("/package/{hid}/{id}")
    ResponseEntity<Hospital> get(@PathVariable("id") String hid, @PathVariable("pid") String pid)
    {
        UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);

        return userRating.getRatings().stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                })
                .collect(Collectors.toList());


        return ResponseEntity.status(HttpStatus.OK).body(hospitalDAO.get(key));

    }

    @GetMapping("/review/{hid}/{pid}/{id}")
    ResponseEntity<Hospital> get(@PathVariable("hid") String id, @PathVariable("pid") String pid, @PathVariable("rid") String rid)
    {
        UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);

        return userRating.getRatings().stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                })
                .collect(Collectors.toList());


        return ResponseEntity.status(HttpStatus.OK).body(hospitalDAO.get(key));
    }*/

}

package day26workshop.gameapp.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import day26workshop.gameapp.model.Game;

@Repository
public class BoardgameRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Game> getAllGames(Integer offset, Integer limit) {
        
        Query query = new Query();
        Pageable pageable = PageRequest.of(offset, limit);
        query.with(pageable);

        return mongoTemplate.find(query, Document.class, "games")
            .stream()
            .map(d -> Game.create(d))
            .toList();
    }

    public List<Game> getSortedBoardGames(Integer offset, Integer limit) {

        Query query = new Query();
        Pageable pageable = PageRequest.of(offset, limit);
        query.with(pageable);
        query.with(Sort.by(Sort.Direction.ASC, "ranking"));

        return mongoTemplate.find(query, Document.class, "games")
            .stream()
            .map(d -> Game.create(d))
            .toList();
    }

    public Game getGameById(Integer gameId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("gid").is(gameId));

        return mongoTemplate.findOne(query, Game.class, "games");
    }
    
}

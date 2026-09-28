package com.sof.futbinclone.Player;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.ArrayList;

public class PlayerSpecification {

    public static Specification<Player> filterBy(PlayerSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(criteria.getMinRating() != null) { //checks if this is a condition that has actually been searched for
                predicates.add(cb.greaterThanOrEqualTo(root.get("rating"), criteria.getMinRating())); //adds a predicate (condition) to the list of conditions by getting a players rating and comparing to the min value
            }

            if(criteria.getMaxRating() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("rating"), criteria.getMaxRating()));
            }

            if(criteria.getMinDribbling() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dribbling"), criteria.getMinDribbling()));
            }

            if(criteria.getMaxDribbling() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dribbling"), criteria.getMaxDribbling()));
            }

            if(criteria.getMinPace() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("pace"), criteria.getMinPace()));
            }

            if(criteria.getMaxPace() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("pace"), criteria.getMaxPace()));
            }

            if(criteria.getMinPassing() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("passing"), criteria.getMinPassing()));
            }

            if(criteria.getMaxPassing() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("passing"), criteria.getMaxPassing()));
            }

            if(criteria.getMinDefending() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("defending"), criteria.getMinDefending()));
            }

            if(criteria.getMaxDefending() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("defending"), criteria.getMaxDefending()));
            }

            if(criteria.getMinPhysical() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("physical"), criteria.getMinPhysical()));
            }

            if(criteria.getMaxPhysical() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("physical"), criteria.getMaxPhysical()));
            }

            if(criteria.getMinShooting() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("shooting"), criteria.getMinShooting()));
            }

            if(criteria.getMaxShooting() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("shooting"), criteria.getMaxShooting()));
            }

            if(criteria.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), criteria.getMinPrice()));
            }

            if(criteria.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), criteria.getMaxPrice()));
            }

            if(criteria.getSm() != null) {
                predicates.add(cb.equal(root.get("sm"), criteria.getSm()));
            }

            if(criteria.getWf() != null) {
                predicates.add(cb.equal(root.get("wf"), criteria.getWf()));
            }

            if(criteria.getLeague() != null) {
                predicates.add(cb.equal(root.get("team").get("league").get("name"), criteria.getLeague()));
            }

            if(criteria.getNation() != null) {
                predicates.add(cb.equal(root.get("nation"), criteria.getNation()));
            }

            if(criteria.getPosition() != null) {
                predicates.add(cb.equal(root.get("position"), criteria.getPosition()));
            }

            if(criteria.getTeam() != null) {
                predicates.add(cb.equal(root.get("team").get("name"), criteria.getTeam()));
            }

            if(criteria.getCardType() != null) {
                predicates.add(cb.equal(root.get("cardType"), criteria.getCardType()));
            }

            if(criteria.getPreferredFoot() != null) {
                predicates.add(cb.equal(root.get("preferredFoot"), criteria.getPreferredFoot()));
            }

            if(criteria.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), criteria.getGender()));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            //return type for criteria builder addition is varargs of predicates, cannot take list so convert into array which is allowed

        };
    }
}

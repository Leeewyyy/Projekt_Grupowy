package pl.lokalnylekarz.projekt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lokalnylekarz.projekt.model.SearchDetails;
import pl.lokalnylekarz.projekt.repository.SearchStatisticsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SearchStatisticsService {
    private final SearchStatisticsRepository repository;

    @Autowired
    public SearchStatisticsService(SearchStatisticsRepository repository) {
        this.repository = repository;
    }

    public void addOrUpdateSearchDetail(SearchDetails searchDetail) {
        Optional<SearchDetails> existingSearch = repository.findBySearchTypeAndValue(searchDetail.getSearchType(), searchDetail.getValue());

        if (existingSearch.isPresent()) {
            SearchDetails existingDetail = existingSearch.get();
            existingDetail.setHits(existingDetail.getHits() + 1);
        } else {
            searchDetail.setHits(1);
        }

        repository.save(searchDetail);
    }

    public void updateGlobalSearch() {
        SearchDetails global = repository.findBySearchType("Global").orElse(new SearchDetails());
        global.setSearchType("Global");
        global.setHits(global.getHits() + 1);
        repository.save(global);
    }


    public List<SearchDetails> getAllSearchDetails() {
        return repository.findAll();
    }

    public SearchDetails getGlobalSearch() {
        return repository.findBySearchType("Global").orElse(new SearchDetails());
    }
}

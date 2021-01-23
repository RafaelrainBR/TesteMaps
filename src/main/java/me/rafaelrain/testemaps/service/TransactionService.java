package me.rafaelrain.testemaps.service;

import lombok.RequiredArgsConstructor;
import me.rafaelrain.testemaps.model.Transaction;
import me.rafaelrain.testemaps.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public Optional<Transaction> findById(Long id) {
        return repository.findById(id);
    }

    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

package com.raj.app.service.impl;

import com.raj.app.service.BookingSlotService;
import com.raj.app.domain.BookingSlot;
import com.raj.app.repository.BookingSlotRepository;
import com.raj.app.service.dto.BookingSlotDTO;
import com.raj.app.service.mapper.BookingSlotMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BookingSlot}.
 */
@Service
@Transactional
public class BookingSlotServiceImpl implements BookingSlotService {

    private final Logger log = LoggerFactory.getLogger(BookingSlotServiceImpl.class);

    private final BookingSlotRepository bookingSlotRepository;

    private final BookingSlotMapper bookingSlotMapper;

    public BookingSlotServiceImpl(BookingSlotRepository bookingSlotRepository, BookingSlotMapper bookingSlotMapper) {
        this.bookingSlotRepository = bookingSlotRepository;
        this.bookingSlotMapper = bookingSlotMapper;
    }

    @Override
    public BookingSlotDTO save(BookingSlotDTO bookingSlotDTO) {
        log.debug("Request to save BookingSlot : {}", bookingSlotDTO);
        BookingSlot bookingSlot = bookingSlotMapper.toEntity(bookingSlotDTO);
        bookingSlot = bookingSlotRepository.save(bookingSlot);
        return bookingSlotMapper.toDto(bookingSlot);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingSlotDTO> findAll() {
        log.debug("Request to get all BookingSlots");
        return bookingSlotRepository.findAll().stream()
            .map(bookingSlotMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BookingSlotDTO> findOne(Long id) {
        log.debug("Request to get BookingSlot : {}", id);
        return bookingSlotRepository.findById(id)
            .map(bookingSlotMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookingSlot : {}", id);
        bookingSlotRepository.deleteById(id);
    }
}

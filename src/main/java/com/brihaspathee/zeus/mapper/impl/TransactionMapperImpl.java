package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.entity.TransactionStatus;
import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.dto.transaction.TransactionStatusDto;
import com.brihaspathee.zeus.mapper.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 1:48 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionMapperImpl implements TransactionMapper {

    /**
     * Broker mapper instance
     */
    private final BrokerMapper brokerMapper;

    /**
     * Payer mapper instance
     */
    private final PayerMapper payerMapper;

    /**
     * Sponsor mapper instance
     */
    private final SponsorMapper sponsorMapper;

    /**
     * Trading Partner mapper instance
     */
    private final TradingPartnerMapper tradingPartnerMapper;

    /**
     * Attributes mapper instance
     */
    private final TransactionAttributesMapper attributesMapper;

    /**
     * Transaction Detail mapper instance
     */
    private final TransactionDetailMapper detailMapper;

    /**
     * Transaction Rate mapper instance
     */
    private final TransactionRateMapper rateMapper;

    /**
     * Transaction Status mapper instance
     */
    private final TransactionStatusMapper statusMapper;

    /**
     * Member mapper instance
     */
    private final MemberMapper memberMapper;

    /**
     * Convert transaction entity to transaction dto
     * @param transaction
     * @return
     */
    @Override
    public TransactionDto transactionToTransactionDto(Transaction transaction) {
        if(transaction == null){
            return null;
        }
        TransactionDto transactionDto = TransactionDto.builder()
                .transactionSK(transaction.getTransactionSK())
                .ztcn(transaction.getZtcn())
                .zfcn(transaction.getZfcn())
                .transactionReceivedDate(transaction.getTransactionReceivedDate())
                .source(transaction.getSource())
                .transactionDetail(detailMapper.detailToDetailDto(transaction.getTransactionDetail()))
                .tradingPartnerDto(tradingPartnerMapper.tpToTpDto(transaction.getTradingPartner()))
                .sponsor(sponsorMapper.sponsorToSponsorDto(transaction.getSponsor()))
                .payer(payerMapper.payerToPayerDto(transaction.getPayer()))
                .broker(brokerMapper.brokerToBrokerDto(transaction.getBroker()))
                .createdDate(transaction.getCreatedDate())
                .updatedDate(transaction.getUpdatedDate())
                .build();
        // Check and map members
        if(transaction.getMembers() != null && transaction.getMembers().size() > 0){
            transactionDto.setMembers(memberMapper.membersToMemberDtos(transaction.getMembers()));
        }
        // Check and map attributes
        if(transaction.getTransactionAttributes() != null && transaction.getTransactionAttributes().size() > 0){
            transactionDto.setTransactionAttributes(attributesMapper.attributesToAttributeDtos(transaction.getTransactionAttributes()));
        }
        // Check and map rates
        if(transaction.getTransactionRates() != null && transaction.getTransactionRates().size() > 0){
            transactionDto.setTransactionRates(rateMapper.ratesToRateDtos(transaction.getTransactionRates()));
        }
        // Check and map statuses
        if(transaction.getTransactionStatus() != null && transaction.getTransactionStatus().size() > 0){
            TransactionStatus transactionStatus =
                    transaction.getTransactionStatus()
                            .stream()
                            .max(
                                    Comparator.comparing(TransactionStatus::getStatusSeq))
                            .get();
            transactionDto.setTransactionStatus(statusMapper.statusToStatusDto(transactionStatus));
        }
        return transactionDto;
    }

    /**
     * Convert transaction dto to transaction entity
     * @param transactionDto
     * @return
     */
    @Override
    public Transaction transactionDtoToTransaction(TransactionDto transactionDto) {
        if(transactionDto == null){
            return null;
        }
        Transaction transaction = Transaction.builder()
                .transactionSK(transactionDto.getTransactionSK())
                .ztcn(transactionDto.getZtcn())
                .zfcn(transactionDto.getZfcn())
                .transactionReceivedDate(transactionDto.getTransactionReceivedDate())
                .source(transactionDto.getSource())
                .transactionDetail(detailMapper.detailDtoToDetail(transactionDto.getTransactionDetail()))
                .tradingPartner(tradingPartnerMapper.tpDtoToTp(transactionDto.getTradingPartnerDto()))
                .sponsor(sponsorMapper.sponsorDtoToSponsor(transactionDto.getSponsor()))
                .payer(payerMapper.payerDtoToPayer(transactionDto.getPayer()))
                .broker(brokerMapper.brokerDtoToBroker(transactionDto.getBroker()))
                .createdDate(transactionDto.getCreatedDate())
                .updatedDate(transactionDto.getUpdatedDate())
                .build();
        // Check and map members
        if(transactionDto.getMembers() != null && transactionDto.getMembers().size() > 0){
            transaction.setMembers(memberMapper.memberDtosToMembers(transactionDto.getMembers()));
        }
        // Check and map attributes
        if(transactionDto.getTransactionAttributes() != null && transactionDto.getTransactionAttributes().size() > 0){
            transaction.setTransactionAttributes(attributesMapper.attributeDtosToAttribute(transactionDto.getTransactionAttributes()));
        }
        // Check and map rates
        if(transactionDto.getTransactionRates() != null && transactionDto.getTransactionRates().size() > 0){
            transaction.setTransactionRates(rateMapper.rateDtosToRates(transactionDto.getTransactionRates()));
        }
        // TODO - Check and map statuses - This may have to done outside, but needs to analyzed
        return transaction;
    }

    /**
     * Convert transaction entities to transaction dtos
     * @param transactions
     * @return
     */
    @Override
    public List<TransactionDto> transactionsToTransactionDtos(List<Transaction> transactions) {

        return transactions.stream().map(this::transactionToTransactionDto).collect(Collectors.toList());
    }

    /**
     * Convert transaction dto to transaction entities
     * @param transactionDtos
     * @return
     */
    @Override
    public List<Transaction> transactionDtosToTransactions(List<TransactionDto> transactionDtos) {

        return transactionDtos.stream().map(this::transactionDtoToTransaction).collect(Collectors.toList());
    }
}

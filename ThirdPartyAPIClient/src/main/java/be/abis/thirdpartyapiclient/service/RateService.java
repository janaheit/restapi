package be.abis.thirdpartyapiclient.service;

public interface RateService {
    double getExchangeRate(String fromCur, String toCur);
}

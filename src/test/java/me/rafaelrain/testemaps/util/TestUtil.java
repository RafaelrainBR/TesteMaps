package me.rafaelrain.testemaps.util;

import me.rafaelrain.testemaps.enums.AssetType;
import me.rafaelrain.testemaps.model.Asset;
import me.rafaelrain.testemaps.model.User;
import me.rafaelrain.testemaps.repository.AssetRepository;
import me.rafaelrain.testemaps.repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtil {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public static Date getDateFrom(String text) throws ParseException {
        return DATE_FORMAT.parse(text);
    }

    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static User getDummyUser() {
        return User.builder()
                .id(26L)
                .name("User only for tests")
                .balance(498D)
                .build();
    }

    public static User createNewUser(UserRepository userRepository) {
        return userRepository.save(getDummyUser());
    }

    public static Asset.Body getDummyAsset() throws Exception {
        return Asset.Body.builder()
                .name("SP-6481RF")
                .price(4.012)
                .type(AssetType.RF)
                .emissionDate(getDateFrom("10-06-2021"))
                .expirationDate(getDateFrom("20-06-2021"))
                .build();
    }

    public static Asset createNewAsset(AssetRepository assetRepository) throws Exception {
        final Asset asset = getDummyAsset().toAsset();
        asset.setId(26L);
        return assetRepository.save(asset);
    }
}

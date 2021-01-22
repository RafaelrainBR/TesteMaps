package me.rafaelrain.testemaps.configuration;

import me.rafaelrain.testemaps.enums.AssetType;
import me.rafaelrain.testemaps.model.Asset;
import me.rafaelrain.testemaps.model.User;
import me.rafaelrain.testemaps.service.AssetService;
import me.rafaelrain.testemaps.service.MovementService;
import me.rafaelrain.testemaps.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserService userService, AssetService assetService, MovementService movements) {

        return args -> {
            final Asset firstAsset = Asset.builder()
                    .name("First Asset")
                    .marketPrice(6.023548)
                    .type(AssetType.RV)
                    .emissionDate(new Date(1611167289))
                    .expirationDate(new Date(1611599289))
                    .build();
            assetService.save(firstAsset);

            final User firstUser = User.builder()
                    .name("First User")
                    .balance(350)
                    .build();
            userService.save(firstUser);

            movements.buyAssets(firstUser, firstAsset, 35, firstAsset.getMarketPrice(), new Date(1611179289));

            System.out.println("User: " + firstUser.toString());
        };
    }
}
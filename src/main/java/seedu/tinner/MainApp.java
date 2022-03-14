package seedu.tinner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.tinner.commons.core.Config;
import seedu.tinner.commons.core.LogsCenter;
import seedu.tinner.commons.core.Version;
import seedu.tinner.commons.exceptions.DataConversionException;
import seedu.tinner.commons.util.ConfigUtil;
import seedu.tinner.commons.util.StringUtil;
import seedu.tinner.logic.Logic;
import seedu.tinner.logic.LogicManager;
import seedu.tinner.model.CompanyList;
import seedu.tinner.model.Model;
import seedu.tinner.model.ModelManager;
import seedu.tinner.model.ReadOnlyCompanyList;
import seedu.tinner.model.ReadOnlyUserPrefs;
import seedu.tinner.model.UserPrefs;
import seedu.tinner.model.util.SampleDataUtil;
import seedu.tinner.storage.CompanyListStorage;
import seedu.tinner.storage.JsonCompanyListStorage;
import seedu.tinner.storage.JsonUserPrefsStorage;
import seedu.tinner.storage.Storage;
import seedu.tinner.storage.StorageManager;
import seedu.tinner.storage.UserPrefsStorage;
import seedu.tinner.ui.Ui;
import seedu.tinner.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 2, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing CompanyList ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        CompanyListStorage companyListStorage = new JsonCompanyListStorage(userPrefs.getCompanyListFilePath());
        storage = new StorageManager(companyListStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyCompanyList> addressBookOptional;
        ReadOnlyCompanyList initialData;
        try {
            addressBookOptional = storage.readCompanyList();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample CompanyList");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleCompanyList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty CompanyList");
            initialData = new CompanyList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty CompanyList");
            initialData = new CompanyList();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty CompanyList");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting CompanyList " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}

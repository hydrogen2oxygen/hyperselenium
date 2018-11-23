package net.hydrogen2oxygen.hyperselenium.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.hydrogen2oxygen.hyperselenium.domain.commands.ClickCommand;
import net.hydrogen2oxygen.hyperselenium.domain.commands.InsertTextCommand;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = ClickCommand.class, name = "ClickCommand"),
        @JsonSubTypes.Type(value = InsertTextCommand.class, name = "InsertTextCommand")})
public interface ICommand {

    CommandResult executeCommand(HyperWebDriver driver);
}
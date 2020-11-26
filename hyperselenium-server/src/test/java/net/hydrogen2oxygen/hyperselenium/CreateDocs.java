package net.hydrogen2oxygen.hyperselenium;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import net.hydrogen2oxygen.hyperselenium.commands.ClickCommand;
import net.hydrogen2oxygen.hyperselenium.domain.HyperseleniumCommand;
import net.hydrogen2oxygen.hyperselenium.domain.ICommand;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class CreateDocs {

    @Test
    public void testCreateDocs() throws Exception {

        final String packageName = ClickCommand.class.getPackage().getName();
        final String packagePath = "src/main/java/" + packageName.replaceAll("\\.","/") + "/";

        final StringBuilder str = new StringBuilder();
        str.append("# HYPERSELENIUM SCRIPT\n");
        str.append("Automatically generated from the package ");
        str.append(packageName);
        str.append("\n");

        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(packageName)
                .scan()) {
            ClassInfoList checked = scanResult.getClassesWithAnnotation(HyperseleniumCommand.class.getCanonicalName());

            checked.forEach(classInfo -> {
                File mdFile = new File(packagePath + classInfo.getSimpleName() + ".md");

                try {
                    ICommand command = (ICommand) Class.forName(classInfo.getName()).newInstance();
                    str.append("## " + command.getCommandName());
                    str.append("\n");

                    if (mdFile.exists()) {
                        List<String> lines = FileUtils.readLines(mdFile, "UTF-8");

                        for (String line : lines) {
                            str.append(line);
                            str.append("\n");
                        }
                    } else {
                        str.append(command.getSyntax());
                    }

                    str.append("\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        FileUtils.writeStringToFile(new File("docs/script.md"), str.toString(), "UTF-8");
    }
}

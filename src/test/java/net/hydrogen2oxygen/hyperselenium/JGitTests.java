package net.hydrogen2oxygen.hyperselenium;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.StatusCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Test;

import java.io.File;

public class JGitTests {

    @Test
    public void test() throws Exception {
        File repoDirectory = new File("target/javaDiff");

        if (repoDirectory.isDirectory() && repoDirectory.listFiles().length > 0) {

            FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
            repositoryBuilder.findGitDir( repoDirectory );
            repositoryBuilder.setMustExist( true );
            Repository repository = repositoryBuilder.build();

            Git git = new Git(repository);

            StoredConfig config = git.getRepository().getConfig();
            //config.setBoolean("core", null, "bare", false);
            //config.save();

            System.out.println(git.getRepository().getConfig().toText());
            StatusCommand statusCommand = git.status();
            statusCommand.call();

            git.pull().call();

        } else {

            Git git = Git.cloneRepository()
                    .setURI("https://github.com/hydrogen2oxygen/javaDiff.git")
                    .setDirectory(new File("target/javaDiff"))
                    .call();

        }
    }
}

package com.github.rvesse.airline.examples.cli;

import com.github.rvesse.airline.Cli;
import com.github.rvesse.airline.Cli.CliBuilder;
import com.github.rvesse.airline.examples.ExampleExecutor;
import com.github.rvesse.airline.examples.ExampleRunnable;
import com.github.rvesse.airline.examples.cli.commands.Help;
import com.github.rvesse.airline.examples.inheritance.Child;
import com.github.rvesse.airline.examples.inheritance.GoodGrandchild;
import com.github.rvesse.airline.examples.inheritance.Parent;
import com.github.rvesse.airline.examples.simple.Required;
import com.github.rvesse.airline.examples.simple.RequiredArguments;
import com.github.rvesse.airline.examples.simple.Simple;

/**
 * An example of creating a CLI with abbreviation enabled
 * <p>
 * Command/Group and/or Option abbreviation allows you to configure Airline such
 * that it permits the names of groups, commands and options provided that those
 * names are unambiguous.  For example the following abbreviation would be acceptable:
 * </p>
 * <pre>
 * in par
 * </pre>
 * <p>
 * Whereas the following would not (because it is ambiguous):
 * </p>
 * <pre>
 * basic req
 * </pre>
 */
public class AbbreviationCli {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        //@formatter:off
        // The program name is cli
        CliBuilder<ExampleRunnable> builder = Cli.<ExampleRunnable>builder("cli")
                                                 // Add a description
                                                 .withDescription("A simple CLI with several commands available in groups")
                                                 // We can enable command and option abbreviation, this allows users to only
                                                 // type part of the group/command/option name provided that the portion they
                                                 // type is unambiguous
                                                 .withCommandAbbreviation()
                                                 .withOptionAbbreviation();
        // Add a basic group
        builder.withGroup("basic")
               .withDescription("Basic commands")
               .withCommands(Simple.class, Required.class, RequiredArguments.class);
        // Add another group
        builder.withGroup("inheritance")
               .withDescription("Commands that demonstrate option inheritance")
               .withCommands(Parent.class, Child.class, GoodGrandchild.class);
        // You can still define top level commands as well 
        builder.withCommand(Help.class);
        //@formatter:on

        ExampleExecutor.executeCli(builder.build(), args);
    }

}

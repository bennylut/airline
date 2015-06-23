package com.github.rvesse.airline.parser;

import com.github.rvesse.airline.Context;
import com.github.rvesse.airline.model.CommandGroupMetadata;
import com.github.rvesse.airline.model.CommandMetadata;
import com.github.rvesse.airline.model.GlobalMetadata;
import com.github.rvesse.airline.model.OptionMetadata;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.List;

public class ParseState<T>
{
    private final List<Context> locationStack;
    private final GlobalMetadata<T> global;
    private final CommandGroupMetadata group;
    private final CommandMetadata command;
    private final ListMultimap<OptionMetadata, Object> parsedOptions;
    private final List<Object> parsedArguments;
    private final OptionMetadata currentOption;
    private final List<String> unparsedInput; 

    ParseState(
            GlobalMetadata<T> global,
            CommandGroupMetadata group,
            CommandMetadata command,
            ListMultimap<OptionMetadata, Object> parsedOptions,
            List<Context> locationStack,
            List<Object> parsedArguments,
            OptionMetadata currentOption,
            List<String> unparsedInput)
    {
        this.global = global;
        this.group = group;
        this.command = command;
        this.parsedOptions = parsedOptions;
        this.locationStack = locationStack;
        this.parsedArguments = parsedArguments;
        this.currentOption = currentOption;
        this.unparsedInput = unparsedInput;
    }

    public static <T> ParseState<T> newInstance()
    {
        return new ParseState<T>(null, null, null, ArrayListMultimap.<OptionMetadata, Object>create(), ImmutableList.<Context>of(), ImmutableList.of(), null, ImmutableList.<String>of());
    }

    public ParseState<T> pushContext(Context location)
    {
        ImmutableList<Context> locationStack = ImmutableList.<Context>builder()
                .addAll(this.locationStack)
                .add(location)
                .build();

        return new ParseState<T>(global, group, command, parsedOptions, locationStack, parsedArguments, currentOption, unparsedInput);
    }

    public ParseState<T> popContext()
    {
        ImmutableList<Context> locationStack = ImmutableList.copyOf(this.locationStack.subList(0, this.locationStack.size() - 1));
        return new ParseState<T>(global, group, command, parsedOptions, locationStack, parsedArguments, currentOption, unparsedInput);
    }

    public ParseState<T> withOptionValue(OptionMetadata option, Object value)
    {
        ImmutableListMultimap<OptionMetadata, Object> newOptions = ImmutableListMultimap.<OptionMetadata, Object>builder()
                .putAll(parsedOptions)
                .put(option, value)
                .build();

        return new ParseState<T>(global, group, command, newOptions, locationStack, parsedArguments, currentOption, unparsedInput);
    }
    
    public ParseState<T> withGlobal(GlobalMetadata<T> global)
    {
        return new ParseState<T>(global, group, command, parsedOptions, locationStack, parsedArguments, currentOption, unparsedInput);
    }

    public ParseState<T> withGroup(CommandGroupMetadata group)
    {
        return new ParseState<T>(global, group, command, parsedOptions, locationStack, parsedArguments, currentOption, unparsedInput);
    }

    public ParseState<T> withCommand(CommandMetadata command)
    {
        return new ParseState<T>(global, group, command, parsedOptions, locationStack, parsedArguments, currentOption, unparsedInput);
    }

    public ParseState<T> withOption(OptionMetadata option)
    {
        return new ParseState<T>(global, group, command, parsedOptions, locationStack, parsedArguments, option, unparsedInput);
    }

    public ParseState<T> withArgument(Object argument)
    {
        ImmutableList<Object> newArguments = ImmutableList.<Object>builder()
                .addAll(parsedArguments)
                .add(argument)
                .build();

        return new ParseState<T>(global, group, command, parsedOptions, locationStack, newArguments, currentOption, unparsedInput);
    }


    public ParseState<T> withUnparsedInput(String input)
    {
        ImmutableList<String> newUnparsedInput = ImmutableList.<String>builder()
                .addAll(unparsedInput)
                .add(input)
                .build();

        return new ParseState<T>(global, group, command, parsedOptions, locationStack, parsedArguments, currentOption, newUnparsedInput);
    }

    @Override
    public String toString()
    {
        return "ParseState{" +
                "locationStack=" + locationStack +
                ", global=" + global + 
                ", group=" + group +
                ", command=" + command +
                ", parsedOptions=" + parsedOptions +
                ", parsedArguments=" + parsedArguments +
                ", currentOption=" + currentOption +
                ", unparsedInput=" + unparsedInput +
                '}';
    }

    public Context getLocation()
    {
        return locationStack.get(locationStack.size() - 1);
    }
    
    public GlobalMetadata<T> getGlobal()
    {
        return global;
    }

    public CommandGroupMetadata getGroup()
    {
        return group;
    }

    public CommandMetadata getCommand()
    {
        return command;
    }

    public OptionMetadata getCurrentOption()
    {
        return currentOption;
    }

    public ListMultimap<OptionMetadata, Object> getParsedOptions()
    {
        return parsedOptions;
    }

    public List<Object> getParsedArguments()
    {
        return parsedArguments;
    }

    public List<String> getUnparsedInput()
    {
        return unparsedInput;
    }
}

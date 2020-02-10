package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.IPQuery;

import java.io.*;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser implements IPQuery {

    Path pathLogs;
    Set<String> uniqueIPs;
    File[] files;

    LogParser (Path logDir) {
        this.pathLogs = logDir;
    }


    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        this.uniqueIPs = new HashSet<>();

        files = pathLogs.toFile().listFiles();
        assert files != null;
        for (File file : files) {
            try {
                FileReader reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader);

                while (br.ready()) {
                    String s = br.readLine();
                    Pattern pattern = Pattern.compile("[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{4}\\s[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}");
                    Matcher matcher = pattern.matcher(s);

                    String date = null;
                    while (matcher.find()) {
                        date = matcher.group();
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("d.M.y H:m:s");
                    Date eventDate = sdf.parse(date);

                    Pattern patternForIP = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
                    Matcher matcherForIP = patternForIP.matcher(s);

                    String findedIP = null;
                    while (matcherForIP.find()) {
                        findedIP = matcherForIP.group();
                    }

                    if (after == null && before != null) {
                        if (eventDate.getTime() <= before.getTime()) {
                            uniqueIPs.add(findedIP);
                        }
                    }else if (after != null && before == null) {
                        if (eventDate.getTime() >= after.getTime()) {
                            uniqueIPs.add(findedIP);
                        }
                    }else if (after == null) {
                        uniqueIPs.add(findedIP);
                    }else {
                        if (eventDate.getTime() >= after.getTime() && eventDate.getTime() <= before.getTime()) {
                            uniqueIPs.add(findedIP);
                        }
                    }
                }
                br.close();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
        return uniqueIPs.size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        getNumberOfUniqueIPs(after,before);
        return uniqueIPs;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        getNumberOfUniqueIPs(after, before);

        Set<String> IPsForUser = new HashSet<>();

        assert files != null;
        for (File file : files) {
            try {
                FileReader reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader);

                while (br.ready()) {
                    String line = br.readLine();

                    Pattern usersPattern = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\s+)([a-zA-Z\\s]+)");
                    Matcher usersMatcher = usersPattern.matcher(line);

                    while (usersMatcher.find()) {
                        String foundedUser = usersMatcher.group(2).trim();
                        if (foundedUser.equals(user)){
                            String foundedIP = usersMatcher.group(1);
                            IPsForUser.add(foundedIP.trim());
                        }
                    }
                }
                br.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return IPsForUser;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        return null;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        return null;
    }
}
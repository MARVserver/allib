package marv.allib.registry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record VersionRange(String range) {

    private static final Pattern SEMVER_PATTERN = Pattern.compile(
        "(>=|<=|>|<|=|>=?|<=?)?(\\d+)\\.(\\d+)\\.(\\d+)(?:-([a-zA-Z0-9.-]+))?"
    );

    public boolean matches(String version) {
        if (version == null || version.isEmpty()) {
            return false;
        }

        String[] parts = range.split(",");
        for (String part : parts) {
            String trimmed = part.trim();
            if (!matchSingle(trimmed, version)) {
                return false;
            }
        }
        return true;
    }

    private boolean matchSingle(String constraint, String version) {
        Matcher matcher = SEMVER_PATTERN.matcher(constraint);
        if (!matcher.matches()) {
            return constraint.equals(version);
        }

        String operator = matcher.group(1);
        int major = Integer.parseInt(matcher.group(2));
        int minor = Integer.parseInt(matcher.group(3));
        int patch = Integer.parseInt(matcher.group(4));

        int[] target = {major, minor, patch};
        int[] current = parseVersion(version);

        if (current == null) {
            return false;
        }

        if (operator == null || operator.isEmpty()) {
            return equals(current, target);
        }

        return switch (operator) {
            case ">=" -> compare(current, target) >= 0;
            case "<=" -> compare(current, target) <= 0;
            case ">" -> compare(current, target) > 0;
            case "<" -> compare(current, target) < 0;
            case "=", "==" -> equals(current, target);
            default -> equals(current, target);
        };
    }

    private int[] parseVersion(String version) {
        String[] parts = version.split("[.-]");
        if (parts.length < 3) {
            return null;
        }
        try {
            return new int[]{
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2].replaceAll("[^0-9]", ""))
            };
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private int compare(int[] a, int[] b) {
        for (int i = 0; i < 3; i++) {
            if (a[i] != b[i]) {
                return Integer.compare(a[i], b[i]);
            }
        }
        return 0;
    }

    private boolean equals(int[] a, int[] b) {
        return a[0] == b[0] && a[1] == b[1] && a[2] == b[2];
    }

    public static VersionRange parse(String range) {
        return new VersionRange(range);
    }
}

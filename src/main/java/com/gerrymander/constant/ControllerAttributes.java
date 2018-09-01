package com.gerrymander.constant;

public class ControllerAttributes {
	public static final String USER_REGULAR = "user_regular";
	public static final String USER_ADMIN = "user_admin";
	
	public static int YEAR_FROM = 2016;
	public static int YEAR_TO = 2016;
	
	public static final String CSV_VOTES_URL = "161208votes.csv";
	
	public static final String STATE_COLOR_JSON_FORMAT = "us-%s";
	public static final String DISTRICT_COLOR_JSON_FORMAT = "us-%s-%s";
	
	public static final String LOPSIDED_FAIL = "The lopsided wins test compares the win margins of the two parties. "
			+ "In states that are gerrymandered, the party that benefits from the gerrymander will win many seats by"
			+ " small margins, while the opposing party wins a few seats by overwhelming margins. The differences in"
			+ " win margins can be calculated using the two-sample t-test, the most widely used test in all of the "
			+ "sciences.\n\n In %s's %d election, Republicans won their districts with an average "
			+ "of %s percent of the vote, and Democrats won their districts with an average of %s percent "
			+ "of the vote. The difference between the two parties’ win margins indicates %s may be gerrymandered"
			+ " to gain an advantage for %s. The chance that this difference would have arisen by nonpartisan"
			+ " processes alone is %s.\n\n";
	public static final String LOPSIDED_PASS = "The lopsided wins test compares the win margins of the two parties. "
			+ "In states that are gerrymandered, the party that benefits from the gerrymander will win many seats by "
			+ "small margins, while the opposing party wins a few seats by overwhelming margins. The differences in "
			+ "win margins can be calculated using the two-sample t-test, the most widely used test in all of the "
			+ "sciences.\n\nIn %s's %d election, Republicans won their districts with an average of %s percent of the vote, "
			+ "and Democrats won their districts with an average of %s percent of the vote. The difference "
			+ "between the two parties’ win margins does not suggest that the state is gerrymandered.";
	public static final String LOPSIDED_SKIP = "The lopsided wins test compares the win margins of the two parties. "
			+ "In states that are gerrymandered, the party that benefits from the gerrymander will win many seats "
			+ "by small margins, while the opposing party wins a few seats by overwhelming margins. The differences"
			+ " in win margins can be calculated using the two-sample t-test, the most widely used test in all of the "
			+ "sciences.\n\nUnfortunately, the two-sample t-test requires that both parties win at least two seats,"
			+ " and %s won %d in %s in %d.";
	public static final String CONSISTENT_FAIL = "The consistent advantage test compares the median Democratic vote share "
			+ "across all districts to the average (arithmetic mean) Democratic vote share in %s in %d. If the median is "
			+ "larger than the average, the state may be a Democratic gerrymander. If the median is smaller than the average, "
			+ "the state may be a Republican gerrymander. \nIn %s in %d, the median Democratic vote share was %.1f%%, the "
			+ "average Democratic vote share was %.1f%%, and the difference is %.2f%%. This difference suggests %s is gerrymandered "
			+ "to gain an advantage for %s. The chance that this difference would have arisen by nonpartisan processes alone is %.2f%%\n\n";
	public static final String CONSISTENT_PASS = "The consistent advantage test compares the median Democratic vote share "
			+ "across all districts to the average (arithmetic mean) Democratic vote share in %s in %d. If the median is "
			+ "larger than the average, the state may be a Democratic gerrymander. If the median is smaller than the average, "
			+ "the state may be a Republican gerrymander. \nIn %s in %d, the median Democratic vote share was %.1f%%, the "
			+ "average Democratic vote share was %.1f%%, and the difference is %.2f%%. This difference does not suggests %s is gerrymandered. \n\n";
	public static final String CONSISTENT_SKIP = "The consistent advantage test compares the median Democratic vote share "
			+ "across all districts to the average (arithmetic mean) Democratic vote share in %s in %d. If the median is "
			+ "larger than the average, the state may be a Democratic gerrymander. If the median is smaller than the average, "
			+ "the state may be a Republican gerrymander. \nIn %s in %d, the median Democratic vote share was %.1f%%, the "
			+ "average Democratic vote share was %.1f%%, and the difference is %.2f%%. \nIt's important to note that when one "
			+ "party is dominant statewide(here is %s), the mean-median difference becomes less reliable. In that case, other "
			+ "tests for asymmetric advantage may be used, as described in Prof. Wang's Election Law Journal article.\n\n";
	public static final String EFFICIENCY_FAIL = "The efficiency gap test counts the number of votes each party wastes in an election to "
			+ "determine whether either party enjoyed a systematic advantage in turning votes into seats. Here we do calculation using "
			+ "margins for Democratic in %s in %d. If the absolute value of efficiency gap is greater than 8 percent, "
			+ "the state may be a partisian gerrymander. A positive efficiency gap value indicates that Democratic has an advantage; "
			+ "a negative value indicates that Democratic is disadvantaged. \nIn %s in %d, "
			+ "the seat margin for Democratic was %.1f%%, the "
			+ "vote margin for Democratic was %.1f%%, and the efficiency gap is %.2f%%. This efficiency gap suggests %s is gerrymandered "
			+ "to gain an advantage for %s. \n\n";
	public static final String EFFICIENCY_PASS = "The efficiency gap test counts the number of votes each party wastes in an election to "
			+ "determine whether either party enjoyed a systematic advantage in turning votes into seats. Here we do calculation using "
			+ "margins for Democratic in %s in %d. If the absolute value of efficiency gap is greater than 8 percent, "
			+ "the state may be a partisian gerrymander. A positive efficiency gap value indicates that Democratic has an advantage; "
			+ "a negative value indicates that Democratic is disadvantaged. \nIn %s in %d, "
			+ "the seat margin for Democratic was %.1f%%, the "
			+ "vote margin for Democratic was %.1f%%, and the efficiency gap is %.2f%%. This efficiency gap does not suggests %s is gerrymandered.\n\n ";
	public static final String EFFICIENCY_SKIP = "The efficiency gap test counts the number of votes each party wastes in an election to "
			+ "determine whether either party enjoyed a systematic advantage in turning votes into seats. Here we do calculation using "
			+ "margins for Democratic in %s in %d. If the absolute value of efficiency gap is greater than 8 percent, "
			+ "the state may be a partisian gerrymander. A positive efficiency gap value indicates that Democratic has an advantage; "
			+ "a negative value indicates that Democratic is disadvantaged. \nIn %s in %d, "
			+ "the seat margin for Democratic was %.1f%%, the "
			+ "vote margin for Democratic was %.1f%%, and the efficiency gap is %.2f%%.\nIt's important to note that when one "
			+ "party is dominant statewide(here is %s), the efficiency gap becomes less reliable. In that case, other "
			+ "tests for asymmetric advantage may be used, as described in Prof. Wang's Election Law Journal article.\n\n";
	public static final String EXCESS_FAIL = "By comparing %s in %d to all Congressional elections carried out in %d, we can estimate the number of seats "
			+ "Democrats and Republicans might be expected to win. A large deviation from this expected value is not itself evidence of partisan gerrymandering "
			+ "because differences could be caused by any number of factors, but it can be combined with the lopsided wins test and the mean-median difference to "
			+ "estimate the effects of a possible partisan gerrymander. In %s in %d, Democrats won %d out of %d seats with %.1f%% of the statewide vote. Our simulations "
			+ "indicate that, according to nationwide trends in %d, Democrats would be expected to win %.3f seats.\n\nThe difference of %.3f is inconsistent with nationwide "
			+ "trends, and suggests that %s may have been gerrymandered to gain an advantage for %s.";
	public static final String EXCESS_PASS = "By comparing %s in %d to all Congressional elections carried out in %d, we can estimate the number of seats "
			+ "Democrats and Republicans might be expected to win. A large deviation from this expected value is not itself evidence of partisan gerrymandering "
			+ "because differences could be caused by any number of factors, but it can be combined with the lopsided wins test and the mean-median difference to "
			+ "estimate the effects of a possible partisan gerrymander. In %s in %d, Democrats won %d out of %d seats with %.1f%% of the statewide vote. Our simulations "
			+ "indicate that, according to nationwide trends in %d, Democrats would be expected to win %.3f seats.\n\nThe difference of %.3f does not suggest %s was gerrymandered.";
	public static final String EXCESS_SKIP = "By comparing %s in %d to all Congressional elections carried out in %d, we can estimate the number of seats "
			+ "Democrats and Republicans might be expected to win. A large deviation from this expected value is not itself evidence of partisan gerrymandering "
			+ "because differences could be caused by any number of factors, but it can be combined with the lopsided wins test and the mean-median difference to "
			+ "estimate the effects of a possible partisan gerrymander. In %s in %d, Democrats won %d out of %d seats with %.1f%% of the statewide vote. Our simulations "
			+ "indicate that, according to nationwide trends in %d, Democrats would be expected to win %.3f seats.\n\nThe difference of %.3f. \n\nUnfortunately, we could not simulate "
			+ "results from %s in %d. This can occur because the state has a large number of Congressional seats or because the state has particularly complicated political geography.";
}

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class CArenaFighter
{
	public static final int COUNTMAXROLLPLAYER = 3;	

	private static void printCritterInfo(CCharacter pPlayerHero, CCharacter pEnemy)
	{
		System.out.println();

		System.out.println("Enemy '" + pEnemy.getName() + "'\tTotalHealth=" + pEnemy.getTotalHealth() +"\t[basehealth=" + pEnemy.getBaseHealth() + "][bonushealth=" + pEnemy.getBonusHealth() + "][" + "penaltyhealth=" + pEnemy.getPenaltyHealth() +"]");
		System.out.println("\t\tHitpoints=" + pEnemy.getTotalHitPoint() +"\t[baseStrength=" + pEnemy.getBaseStrength() + "]" + "[bonusStrength=" + pEnemy.getBonusStrength() + "]" + "[penaltyStrength=" + pEnemy.getPenaltyStrength() + "]");
		System.out.println();
		System.out.println("Player '" + pPlayerHero.getName() + "'\tTotalHealth=" + pPlayerHero.getTotalHealth() +"\t[basehealth=" + pPlayerHero.getBaseHealth() + "][bonushealth=" + pPlayerHero.getBonusHealth() + "][" + "penaltyhealth=" + pPlayerHero.getPenaltyHealth() +"]");
		System.out.println("\t\tHitpoints=" + pPlayerHero.getTotalHitPoint() +"\t[baseStrength=" + pPlayerHero.getBaseStrength() + "]" + "[bonusStrength=" + pPlayerHero.getBonusStrength() + "]" + "[penaltyStrength=" + pPlayerHero.getPenaltyStrength() + "]");
		System.out.println();
	}
	
	private static void printWinnerAndLoser(CCharacter pPlayerHero, CCharacter pEnemy) 
	{
		if (pPlayerHero.isDead() && pEnemy.isDead())
			System.out.println("The game ended in a draw");
		else if (pPlayerHero.isAlive())
			System.out.println("The player '" + pPlayerHero.getName() + "' has won the game!");
		else if (pEnemy.isAlive()) 
			System.out.println("The EVIL enemy '" + pEnemy.getName() + "' has won the game!");
		else
			System.out.println("This should be unreachable code[printWinnerAndLoser]");
	}
			
	public static void main(String[] args)
	{
		CBattleLog myBattleLog = new CBattleLog();
		
		//Game counters
		int countLevel = 0;
		int countTurns = 0;
		int countRollingPlayer = 0;
		int countRerools = 0;
		
		//Flags
		boolean playerDoneRolling = false;
		boolean playerRetire = false;
						
		//Console input
		Scanner scanner = new Scanner(System.in);
		
		//Name your character
		System.out.print("Name your character? ");
		String characterName = scanner.nextLine();
				
		CCharacter playerHero = new CCharacter(characterName);
		CCharacter enemy = new CCharacter("Joker");		

		//New Level
		//Execute as long as Player is NOT retired
		//Execute as long as player is NOT dead
		do 
		{
			//Increase level
			countLevel++;
			System.out.println();
			System.out.println("\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/");
			System.out.println("Level=" + countLevel);
			System.out.println("\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/");
												
			//New Turn
			do 
			{
				countTurns++;
				System.out.println("****************************************************************************");
				System.out.println("\t\t\tTurn=" + countTurns);
				System.out.println("****************************************************************************");			
				printCritterInfo(playerHero, enemy);
								
				System.out.println();
				System.out.println();
				
				System.out.println("----------------------------- Rolling DICE ----------------------------------------");
				enemy.setDiceHitPoint(CCharacter.rollDice());
				System.out.println("Enemy, '" + enemy.getName() + "' rolls the dice and get a: " + enemy.getDiceHitPoint() + " on Dice" + "\t\t(total hitpoints=" + enemy.getTotalHitPoint() + ")");						
				System.out.println();
				
				//Re-roll 
				//Stop re-roll when player is satisfied with roll outcome
				//If COUNTMAXROLLPLAYER is reached
				do 
				{
					countRollingPlayer++;
					playerHero.setDiceHitPoint(CCharacter.rollDice());
					
					System.out.println("You, '" + playerHero.getName() +"' roll the dice and get a: " + playerHero.getDiceHitPoint() + " on Dice" + "\t\t(total hitpoints=" + playerHero.getTotalHitPoint() + ")");	
					//No more re-rolls
					if (countRollingPlayer == (CArenaFighter.COUNTMAXROLLPLAYER)) 
					{
						playerDoneRolling = true;
						break;
					}
					
					System.out.print("You wish to re-roll, Y/N ? ");
					//Ask player if he/she wishes to save dice number or roll again
					char chYesOrNoReroll = scanner.next().charAt(0);
					
					if (chYesOrNoReroll == 'Y' || chYesOrNoReroll == 'y') 
					{
						countRerools++;
					}
						
					else if (chYesOrNoReroll == 'N' || chYesOrNoReroll == 'n') 
					{
						//Done rolling
						playerDoneRolling = true;	
					}
						
					//This should be unreachable code
					else 
					{
						System.out.println("Should be unreachable code[reroll]");
						System.out.println("User inputed invalid choice for rerolling");
					}						
				}
				//Re-roll until user do NOT want anymore OR rolling count is 3 (COUNTMAXROLLPLAYER)
				while (!playerDoneRolling);
				
				System.out.println("--------------------------- DONE Rolling DICE --------------------------------------");
				System.out.println();
				System.out.println();
				
				//Reset counters for rolling
				countRollingPlayer = 0;
				playerDoneRolling = false;
							
				//Calculate damage to each Critter
				playerHero.substractHealth(enemy.getTotalHitPoint());
				enemy.substractHealth(playerHero.getTotalHitPoint());
				
				//Calculate winning stats for each battle and store battle info
				CBattleEntry myEntry;
				if (enemy.getTotalHitPoint() > playerHero.getTotalHitPoint()) 
				{
					myEntry = new CBattleEntry(playerHero.getName(), enemy.getName(), enemy.getName(), playerHero.getTotalHitPoint(), enemy.getTotalHitPoint() );
				}
					
				else if (enemy.getTotalHitPoint() < playerHero.getTotalHitPoint()) 
				{
					myEntry = new CBattleEntry(playerHero.getName(), enemy.getName(), playerHero.getName(), playerHero.getTotalHitPoint(), enemy.getTotalHitPoint());
				}
					
				else
				{
					myEntry = new CBattleEntry(playerHero.getName(), enemy.getName(),"DRAW", playerHero.getTotalHitPoint(), enemy.getTotalHitPoint() );
				}
				//Add entry to battle log				
				myBattleLog.addBattleEntry(myEntry);	
				System.out.println();
				
			}
			//Execute until somebody dies
			while (playerHero.isAlive() && enemy.isAlive());
			
			//Determine who lost/died 
			if (playerHero.isDead()) 
			{
				System.out.println("Player " + playerHero.getName() + " is dead");
			}
				
			else if (enemy.isDead()) 
			{
				System.out.println("Enemy " + enemy.getName() + " is dead");
				myBattleLog.increaseKillStats(1);
				System.out.print("You wish to retire, Y/N ? ");
				//Ask hero if he wish to save dice number or roll again
				char chRetire = scanner.next().charAt(0);
				if (chRetire == 'Y' || chRetire == 'y')
					playerRetire = true;
				else
				{
					System.out.println("New round and level up");
					enemy = new CCharacter("Joker" + (countLevel+1));
					
					//Very SIMPLE battle point system and not a balanced one...
										
					//Set attributes, bonuses and penalties
					playerHero.reRandomizeAttributeValues();
					//Based on score, increments every 100
					int currentScore = myBattleLog.gameScore(playerHero, countLevel);
					currentScore /= 100;
					//Based on re-roll, increments every 10
					int penaltyBasedRerolls = countRerools;
					penaltyBasedRerolls /= 10;
					playerHero.randomizeBonusAttributes(currentScore);
					playerHero.randomizePenaltyAttributes(penaltyBasedRerolls);
					
					//enemy gets stronger each level is increased
					enemy.randomizeBonusAttributes(countLevel);
					enemy.randomizePenaltyAttributes(0);
				}
			}										
		}
		while(!playerRetire && !(playerHero.isDead()));
		
		printWinnerAndLoser(playerHero, enemy);
		myBattleLog.printBattleHistory();
		myBattleLog.printBattleSummary(playerHero);
								
		//Calculate a score		
		int totalScore = myBattleLog.gameScore(playerHero, countLevel);
		System.out.println(playerHero.getName() + " got a total score of: " + totalScore);				
		
		System.out.println();
		System.out.println("Game stats");
		System.out.println("You reached level:\t" + countLevel);
		System.out.println("Turns needed:\t\t" + countTurns);
		System.out.println("Utilized re-rolls:\t" + countRerools);
		System.out.println();
		
		System.out.println("Game Over");			
	}	
}

class CCharacter 
{
	public static final int MINHEALTHRANDOM = 36;
	public static final int MAXHEALTHRANDOM = 54;
	
	public static final int MINDICENUMBER = 1;
	public static final int MAXDICENUMBER = 6;
	
	//Non mutable attributes
	private final String mName;
	
	//Mutable attributes
	private int mBaseHealth;
	private int mBaseStrength;			// Acts as Hit Points
	private int mDiceHitPoint;			// Acts as Hit Points
			
	//Bonus attributes
	private int mBonusHealth;			// (PowerUp)
	private int mBonusStrength;			// (PowerUp)
	
	//Penalties
	private int mPenaltyHealth;			// (Powerdown)
	private int mPenaltyStrength;		// (Powerdown)
	
	public CCharacter(String pName)
	{
		mName = pName;
		mDiceHitPoint = 0;
		
		//Base attributes
		randomizeBaseAttributeValues();
		//Extra attributes	
		randomizeBonusAttributes(0);
		randomizePenaltyAttributes(0);
	}	
	
	private void resetBaseAttributes() 
	{
		mBaseHealth = 0;
		mBaseStrength = 0;
	}
	
	private void resetBonusAttributes() 
	{
		mBonusHealth = 0;
		mBonusStrength = 0;
	}
	
	private void resetPenaltyAttributes() 
	{
		mPenaltyHealth = 0;
		mPenaltyStrength = 0;
	}
	
	public void reRandomizeAttributeValues() 
	{
		mDiceHitPoint = 0;
		
		//Initialize members for reuse of object
		resetBaseAttributes();
		resetBonusAttributes();
		resetPenaltyAttributes();
				
		//Base attributes
		randomizeBaseAttributeValues();
		//Extra attributes	
		randomizeBonusAttributes(0);
		randomizePenaltyAttributes(0);			
	}
	
	private void randomizeBaseAttributeValues() 
	{
		//As in the specification you gave us 
		mBaseStrength = CCharacter.randomizeNumber(CCharacter.MINDICENUMBER, CCharacter.MAXDICENUMBER);
		//I choose a factor of 6
		mBaseHealth = CCharacter.randomizeNumber(CCharacter.MINHEALTHRANDOM, CCharacter.MAXHEALTHRANDOM);		
	}
	
	//Interval pLowestBonus to (CCharacter.MAXDICENUMBER - 1)
	public void randomizeBonusAttributes(int pLowestBonus) 
	{
		//Cap it
		if (pLowestBonus >= CCharacter.MAXDICENUMBER)
			pLowestBonus = (CCharacter.MAXDICENUMBER - 1);
		
		if (pLowestBonus < 0 )
			pLowestBonus = 0;
		
		mBonusHealth = CCharacter.randomizeNumber(pLowestBonus, CCharacter.MAXDICENUMBER);
		mBonusStrength = CCharacter.randomizeNumber(pLowestBonus, CCharacter.MAXDICENUMBER);
	}
	
	//Interval pLowestBonus to (CCharacter.MAXDICENUMBER - 1)
	public void randomizePenaltyAttributes(int pLowestPenalty) 
	{
		//Cap it
		if (pLowestPenalty >= CCharacter.MAXDICENUMBER)
			pLowestPenalty = (CCharacter.MAXDICENUMBER - 1);
		
		if (pLowestPenalty < 0 )
			pLowestPenalty = 0;
		
		mPenaltyHealth = CCharacter.randomizeNumber(pLowestPenalty, CCharacter.MAXDICENUMBER);
		mPenaltyStrength = CCharacter.randomizeNumber(pLowestPenalty, CCharacter.MAXDICENUMBER);
	}
	
	//Generates a random number between 1 and 6
	public static int rollDice() 
	{
		return CCharacter.randomizeNumber(CCharacter.MINDICENUMBER, CCharacter.MAXDICENUMBER);
	}
	
	//Generates a number between pLowerLimit and pHigherLimit	
	//Make SURE that pHigherLimit > pLowerLimit
	public static int randomizeNumber(int pLowerLimit, int pHigherLimit) 
	{
		Random rand = new Random();
		int randomNumber = (pLowerLimit) + (rand.nextInt(pHigherLimit-pLowerLimit));		
		return randomNumber;
	}
	
	public boolean isDead() 
	{
		if (getTotalHealth() <= 0)
			return true;
		else
			return false;
	}
	
	public boolean isAlive() 
	{
		if (getTotalHealth() > 0)
			return true;
		else
			return false;
	}
	
	
	//Hit point Section
	//Always random
	public int getBaseStrength() { return mBaseStrength; }
	
	//Sets after each roll
	public void setDiceHitPoint(int pStrength) { mDiceHitPoint = pStrength;	}
	
	public int getDiceHitPoint() { return mDiceHitPoint; }
	
	public void setBonusStrength(int pBonusStrength) { mBonusStrength = pBonusStrength;	}
	
	public void setPenaltyStrength(int pPenaltyStrength) { mPenaltyStrength = pPenaltyStrength; }
	
	public int getBonusStrength() { return mBonusStrength; }
	
	public int getPenaltyStrength() { return mPenaltyStrength; }
	
	//Check that this value dont get negative -> Means opponent get health insteed of damage :D
	public int getTotalHitPoint() 
	{
		int totalHitPoint = (getBaseStrength() + getDiceHitPoint() + getBonusStrength() - getPenaltyStrength() );
		if (totalHitPoint < 0 )
			return 0;
		else
			return totalHitPoint;
	}
		
	//Health Section
	//Always random
	public int getBaseHealth() { return mBaseHealth; }
		
	//When inflicted damage	
	public void substractHealth(int pHitPoints) { mBaseHealth -= pHitPoints; }
	
	public void setBonusHealth(int pBonusHealth) { mBonusHealth = pBonusHealth; }
	
	public void setPenaltyHealth(int pPenaltyHealth) { mPenaltyHealth = pPenaltyHealth; }
	
	public int getBonusHealth() { return mBonusHealth; }
	
	public int getPenaltyHealth() {	return mPenaltyHealth; }
		
	//Invoke this when determine the health of player
	public int getTotalHealth() 
	{
		return ( getBaseHealth() + getBonusHealth() - getPenaltyHealth() );
	}
	
	public String getName() { return mName; }

	@Override
	public String toString() 
	{
		return "CCharacter [" +
							"mName=" + mName + 
							", mBaseHealth=" + mBaseHealth + 
							", mBonusHealth=" + mBonusHealth + 
							", mBaseStrength=" + mBaseStrength +
							", mBonusStrength=" + mBonusStrength + 
							", mDiceHitPoint=" + mDiceHitPoint + 							
							", mPenaltyHealth=" + mPenaltyHealth + 
							", mPenaltyStrength=" + mPenaltyStrength + 
							"]";
	}		
}


class CBattleLog 
{
	private ArrayList<CBattleEntry> mListBattles;	
	private int mEnemyKilled;
	
	public CBattleLog() 
	{
		mListBattles = new ArrayList<CBattleEntry>();
		mEnemyKilled=0;
	}
	
	//Setting new High Score? :D
	public int gameScore(CCharacter pPlayerHero, int pLevel) 
	{	
		int aliveScore = 0;
		if (pPlayerHero.isAlive())
			aliveScore = 5;
		int totalScore = (getEnemyKilled() *4) + getPlayerWinsBattle(pPlayerHero)*3 + getPlayerDrawBattle() + 2*pLevel + aliveScore;
		return totalScore;
	}
	
	public void addBattleEntry(CBattleEntry pBattleEntry) { mListBattles.add(pBattleEntry); }
		
	//Detailed stats of battle outcomes
	public void printBattleHistory() 
	{
		System.out.println("Battle History");
		mListBattles.forEach(oneEntry -> System.out.println(oneEntry));
		System.out.println();
	}
	
	//Brief summary of battle outcomes
	public void printBattleSummary(CCharacter pPlayerHero) 
	{
		System.out.println("Battle Summary");
		int countTotalTurns = mListBattles.size();
		System.out.println("The whole war took:\t" + countTotalTurns + " turn(s)");
		System.out.println("Player won:\t\t" + getPlayerWinsBattle(pPlayerHero) + " battle(s)");
		System.out.println("Player lost:\t\t" + getPlayerLossesBattle(pPlayerHero) + " battle(s)");
		System.out.println("Player draw:\t\t" + getPlayerDrawBattle() + " battle(s)");
		System.out.println();
	}
	
	//Behave and DON'T add more than ONE (in this game)
	public void increaseKillStats(int pKillCount) { mEnemyKilled += pKillCount; }
	
	public int getEnemyKilled() { return mEnemyKilled; }
	
	//How many battles did the player win
	private int getPlayerWinsBattle(CCharacter pPlayerHero) 
	{
		int countPlayerWins = 0;
		
		for (CBattleEntry oneEntry : mListBattles) 
		{
			if (pPlayerHero.getName().equals(oneEntry.getWinnerOfBattle()))
				countPlayerWins++;
		}
		return countPlayerWins;
	}
	
	//How many draws
	private int getPlayerDrawBattle() 
	{
		int countPlayerDraw = 0;
		
		for (CBattleEntry oneEntry : mListBattles) 
		{
			if (oneEntry.getWinnerOfBattle().equals("DRAW"))
				countPlayerDraw++;
		}
		return countPlayerDraw;
	}
	
	//How many battles did the player lose
	private int getPlayerLossesBattle(CCharacter pPlayerHero) 
	{
		int countAllBattles = mListBattles.size();
		int countWinAndDraw = getPlayerWinsBattle(pPlayerHero) + getPlayerDrawBattle();
						
		return (countAllBattles - countWinAndDraw);
	}	
}

//Contain information about one battle
class CBattleEntry
{
	//Unique identifiers
	private static long battleIDCounter = 0;
	private long mBattleId;
	//When battle occurred
	private String mTimeStampOfBattle;				
	
	//Battle outcome  
	private String mPlayerName;
	private String mOpponentName;
	private String mWinnerOfBattle;
	
	//Indicates how good the battle went for the player
	private int mPlayerOffsetRatio;
	
	
	public CBattleEntry(String pPlayerName, String pOpponentName, String pWinnerOfBattle, int pTotalHitpointPlayerHero, int pTotalHitpointPlayerEnemty) 
	{
		//Only unique under execution of program
		mBattleId = ++battleIDCounter;
		mTimeStampOfBattle = LocalTime.now().toString();		
		
		mPlayerName = pPlayerName;
		mOpponentName = pOpponentName;
		mWinnerOfBattle = pWinnerOfBattle;
		
		mPlayerOffsetRatio = (pTotalHitpointPlayerHero-pTotalHitpointPlayerEnemty);				
	}
	
	public String getWinnerOfBattle() { return mWinnerOfBattle;	}
	
	public long getBattleId() {	return mBattleId; }

	public String getPlayerName() { return mPlayerName; }

	public String getOpponentName() { return mOpponentName;	}
	
	public String getTimeOfBattle() { return mTimeStampOfBattle; }

	@Override
	public String toString() 
	{
		return "|BattleId:" + mBattleId +
				"\tPlayerName:" + mPlayerName + 
				"\tOpponentName:" + mOpponentName + 
				"\t" + "BatteWinner:" + mWinnerOfBattle + 
				"\tPlayerRatio=" + mPlayerOffsetRatio + 
				"\t TimeStamp=" + mTimeStampOfBattle +
				"\t|";					
	}			
}





public class BlackJack{
	public static void main(String[] args){
		int playerAceCount = 0, dealerAceCount = 0, playerAce3 = 0, dealerAce3 = 0;
		int newPlayerTotal = 0, playerCard3 = 0, newDealerTotal = 0, dealerCard3 = 0;
		
		System.out.println("Welcome to Keri's Casino!");
		System.out.print("Please Enter your name: ");
		java.util.Scanner input = new java.util.Scanner(System.in);
		String playerName = input.next();
		
		//dealing the first two cards
		int dealerCard1 = dealerChoice();
		int dealerAce1 = countAces(dealerCard1);
		int dealerCard2 = dealerChoice();
		int dealerAce2 = countAces(dealerCard2);  
		int dealerTotal = dealerCard1 + dealerCard2;
		System.out.println("\nThe dealer: ");
		System.out.println("? + " + dealerCard1);
		int playerCard1 = playerChoice();
		int playerAce1 = countAces(playerCard1);  
		int playerCard2 = playerChoice();
		int playerAce2 = countAces(playerCard2); 
		int playerTotal = playerCard1 + playerCard2;
		System.out.println("\nYou: ");
		System.out.println(playerCard1 + " + " + playerCard2 + " = " + playerTotal);
		
		//end the game if either get to 21
		if (dealerTotal == 21){
			System.out.println("\nBlackjack! Dealer wins!");
		}
		else if (playerTotal == 21){
			System.out.println("\nBlackjack! You win!");
		}
		
		//ask player if they want to hit or stand
		if (playerTotal != 21 && dealerTotal != 21){
			System.out.print("\nWould you like to \"hit\" or \"stand?\" ");
			String hitOrStand = input.next();
			String playerChoice = hitOrStand.toLowerCase();
			
			//check for valid input
			while(!playerChoice.equals("hit") && !playerChoice.equals("stand")){
				System.out.println("\nWould you like to \"hit\" or \"stand?\"");
				hitOrStand = input.next();
				playerChoice = hitOrStand.toLowerCase();
			}
			if (playerChoice.equals("hit")){
				playerCard3 = playerChoice();
				playerAce3 = countAces(playerCard3);
				newPlayerTotal = playerTotal + playerCard3;
				}
			else if (playerChoice.equals("stand")){
				newPlayerTotal = playerTotal;
				}
			if (dealerTotal < 17){
				dealerCard3 = dealerChoice();
				dealerAce3 = countAces(dealerCard3); 
				newDealerTotal = dealerTotal + dealerCard3;
				}
			else if (dealerTotal >= 17){
				newDealerTotal = dealerTotal;
				}
			dealerAceCount = dealerAce1 + dealerAce2 + dealerAce3;
			playerAceCount = playerAce1 + playerAce2 + playerAce3;
			
		//change value of aces if total over 21
			while(dealerAceCount != 0 && newDealerTotal > 21){
				if (dealerCard1 == 11){
					dealerTotal -= 10;
					newDealerTotal -= 10;
					dealerAceCount -= 1;
				}
				else if (dealerCard2 == 11){
					dealerTotal -= 10;
					newDealerTotal -= 10;
					dealerAceCount -= 1;
				}
				else if (dealerCard3 == 11){
					dealerCard3 = 1;
					dealerTotal -= 10;
					newDealerTotal -= 10;
					dealerAceCount -= 1;
				}
			}
			while(playerAceCount != 0 && newPlayerTotal > 21){
				if (playerCard1 == 11){
					playerTotal -= 10;
					newPlayerTotal -= 10;
					playerAceCount -= 1;
				}
				else if (playerCard2 == 11){
					playerTotal -= 10;
					newPlayerTotal -= 10;
					playerAceCount -= 1;
				}
				else if (playerCard3 == 11){
					playerCard3 = 1;
					newPlayerTotal -= 10;
					playerAceCount -= 1;
				}
			}
		
			//determine winner
			if (newPlayerTotal > 21){
				System.out.println("\nThe dealer: ");
				System.out.println("? + " + dealerCard1);
				System.out.println("\nYou: ");
				System.out.println(playerTotal + " + " + playerCard3 + " = " + newPlayerTotal + " BUSTED!");
				System.out.println("\nYou busted. The dealer wins!");
			}
			else if (newDealerTotal > 21){
				System.out.println("\nThe dealer: ");
				System.out.println(dealerTotal + " + " + dealerCard3 + " = " + newDealerTotal + " BUSTED!");
				System.out.println("\nYou: ");
				System.out.println(playerTotal + " + " + playerCard3 + " = " + newPlayerTotal);
				System.out.println("\nThe dealer busted. You win!");
			}
			else if(newPlayerTotal == newDealerTotal){
				System.out.println("\nThe dealer: ");
				System.out.println("? + " + dealerCard1);
				System.out.println("\nYou: ");
				System.out.println(playerTotal + " + " + playerCard3 + " = " + newPlayerTotal);
				System.out.println("\nIt is a tie. Dealer wins!");
			}
			else if (newPlayerTotal > newDealerTotal){
				System.out.println("\nThe dealer: ");
				System.out.println("? + " + dealerCard1);
				System.out.println("\nYou: ");
				System.out.println(playerTotal + " + " + playerCard3 + " = " + newPlayerTotal);
				if (newPlayerTotal != 21){
				System.out.println("\nYou win!");
				}
				else if (newPlayerTotal == 21){
					System.out.println("\nBlackjack! You win!");
				}
			}
			else if (newPlayerTotal < newDealerTotal){
				System.out.println("\nThe dealer: ");
				System.out.println("? + " + dealerCard1);
				System.out.println("\nYou: ");
				System.out.println(playerTotal + " + " + playerCard3 + " = " + newPlayerTotal);
				if (newDealerTotal != 21){
				System.out.println("\nDealer wins!");
				}
				else if (newDealerTotal == 21){
					System.out.println("\nBlackjack! Dealer wins!");
				}
			}
		}
	}
	
	static int dealerChoice(){
		int dealerCard = dealCard();
		if(dealerCard == 11 || dealerCard == 12 || dealerCard == 13){
			dealerCard = 10;
			}
		if (dealerCard == 1){
			dealerCard = 11;
			}
		return dealerCard;
		}
		
	static int playerChoice(){
		int playerCard = dealCard();
		if(playerCard == 11 || playerCard == 12 || playerCard == 13){
			playerCard = 10;
			}
		if (playerCard == 1){
			playerCard = 11;
			}
		return playerCard;
		}
		
	static int dealCard(){
		return (int)(Math.random() * 13) + 1;
		}
	
	static int countAces(int card){
		int aceCount = 0;
		if (card == 11){
			aceCount += 1;
		}
		return aceCount;
	}
}
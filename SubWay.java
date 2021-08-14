import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Scanner;
import java.util.Random;
import java.util.*;

import java.io.*;

public class SubWay
{
	private static Scanner sc = new Scanner(System.in);

	public static void main(String args[])
	{
		LinkedHashMap<String,Integer> innerMap = new LinkedHashMap();
		LinkedHashMap<String,LinkedHashMap<String,Integer>> ingred = new LinkedHashMap();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		innerMap.put("Hard",10);
		innerMap.put("Thin",10);
		innerMap.put("Soft",12);

		ingred.put("Crust",innerMap);

		innerMap = new LinkedHashMap();

		innerMap.put("Chicken Tikka", 120);
		innerMap.put("Panner Tikka",100);
		innerMap.put("Turkey Meet",130);

		ingred.put("Filling",innerMap);

                innerMap = new LinkedHashMap();
		innerMap.put("Cucumber",25);
		innerMap.put("Tomato",20);
		innerMap.put("Meat strip",45);
		innerMap.put("Cabbage",20);

		ingred.put("Toppings",innerMap);


		System.out.println(" Welcome to Subway ");
		System.out.println(" 1) Order 'Sub' off the day");
		System.out.println(" 2) Order your own 'Sub'");
		System.out.println(" 3) Exit");
		
		int userInput = sc.nextInt();
		HashMap selectedMap = new HashMap();
		if(userInput == 1)
			selectedMap = optionOne(ingred);
		else if(userInput == 2)
			selectedMap = optionSecond(ingred);

		//System.out.println(" selected : "+ selectedMap);

		int invoiceAmount = createInvoice(selectedMap);
		if(invoiceAmount > 0)
			System.out.println(" Invoice Amount : "+ invoiceAmount +" ₹  please enter to confirm order " );
		else
			System.out.println(" Sorry Sir/Madam Please try to order one more time ");

		try{
		input = br.readLine();
		if(invoiceAmount > 0)
			System.out.println("Your subway order is placed for " + invoiceAmount + " ₹" );
		}
		catch(IOException e)
		{
			
		}

	}
	// select option 1 order sub of the day
	private static HashMap optionOne(LinkedHashMap<String,LinkedHashMap<String,Integer>> subwayDetailMap)
	{
		HashMap selectedMap = new HashMap();
		try
		{
			System.out.println("1) Veg    2) Non-Veg    3) AnyOne");
			int input = sc.nextInt();
			if(input < 1 || input > 3)
			{
				System.out.println(" Please select a valid order : like 1 or 2 or 3 ");
				input = sc.nextInt();
			}
			if(input == 1 || input == 2 || input == 3)
			{
				for(String opt : subwayDetailMap.keySet())
				{
					HashMap<String, Integer> map = new HashMap();
					LinkedHashMap<String, Integer> innerMap = subwayDetailMap.get(opt);
					List<String> keysAsArray = new ArrayList<String>(innerMap.keySet()); // for generating random orders
					Random r = new Random();
					if(input == 3)
					{
						if(opt.equals("Toppings"))
						{
							for(int i = 0; i < 3; i++)
							{					
								String value = keysAsArray.get(r.nextInt(keysAsArray.size())); // generate any one key
								if(map.containsKey(value))
									value = keysAsArray.get(r.nextInt(keysAsArray.size()));
								map.put(value,innerMap.get(value));
							}
						}
						else
						{
							String value = keysAsArray.get(r.nextInt(keysAsArray.size()));
							map.put(value,innerMap.get(value));
						}
					}
					else if(input == 1) // for veg
					{
						if(opt.equals("Toppings"))
						{
							innerMap.remove("Meat strip");						
							map = (HashMap)innerMap;
						}
						else if(opt.equals("Filling"))
							map.put("Paneer Tikka",100);
						else
						{
							String value = keysAsArray.get(r.nextInt(keysAsArray.size()));
							map.put(value,innerMap.get(value));
						}
					}
					else if(input == 2) // for non-veg
					{
						if(opt.equals("Filling"))
						{
							innerMap.remove("Paneer Tikka");
							String value = keysAsArray.get(r.nextInt(keysAsArray.size()));
        	                                        map.put(value,innerMap.get(value));
						}
						else if(opt.equals("Toppings"))
						{
							for(int i = 0; i < 3; i++)
                                                	{
                                                        	String value = keysAsArray.get(r.nextInt(keysAsArray.size()));
	                                                        if(map.containsKey(value))
        	                                                        value = keysAsArray.get(r.nextInt(keysAsArray.size()));
                	                                        map.put(value,innerMap.get(value));
								if(!map.containsKey("Meat strip"))
									map.put("Meat strip",innerMap.get("Meat strip"));
                                        	        }
						}
						else
						{
							String value = keysAsArray.get(r.nextInt(keysAsArray.size()));
                                	                map.put(value,innerMap.get(value));
						}
					}
					selectedMap.put(opt,map);
				}
			}
		}
		catch(Exception e)
                {
                        e.printStackTrace();
                }
                return selectedMap;
	}
	// select option 2 own sub
	private static HashMap optionSecond(LinkedHashMap<String,LinkedHashMap<String,Integer>> subwayDetailMap)
	{
		HashMap selectedMap = new HashMap();
		try
		{
			for(String opt : subwayDetailMap.keySet())
			{
				HashMap<String, Integer> map = new HashMap();
				if(opt.equals("Toppings"))
					System.out.println(" - Select \""+opt+"\" (max of 3)");
				else
					System.out.println(" - Select \""+opt+"\" (max of 1)");
				LinkedHashMap<String, Integer> ingredetail = (LinkedHashMap)subwayDetailMap.get(opt);
				ArrayList<String> arlist = new ArrayList();
                                for(String key: ingredetail.keySet())
                                        arlist.add(key);
				int i = 1;
				for(String ingred : ingredetail.keySet())
				{
					System.out.println( i+") "+ingred+"      - "+ingredetail.get(ingred) + " ₹");
					i++;
				}
				if(opt.equals("Toppings"))
				{
					String userStr = sc.next();
					if(!userStr.contains(",") || userStr.length() > 1)
					{
						System.out.println(" Please select a valid order for toppings --- like 1,2,3");
						userStr = sc.next();
					}
					String selectedToppings[] = userStr.split(",");
					for(String value : selectedToppings)
					{
						map.put(arlist.get((Integer.valueOf(value))-1), ingredetail.get(arlist.get((Integer.valueOf(value))-1)));
					}
				}
				else
				{
					int userInput = sc.nextInt();
					map.put(arlist.get(userInput-1), ingredetail.get(arlist.get(userInput-1)));
				}
				selectedMap.put(opt, map);
			}


		}
		catch(Exception e)
		{
		//	e.printStackTrace();
		}
		return selectedMap;
	}
	private static int createInvoice(HashMap<String, HashMap<String, Integer>> selectedMap)
	{
		int totalAmount = 0;
		try
		{
			System.out.println(" - Invoice for Sub ");
			for(String subwayOption : selectedMap.keySet())
			{
				HashMap<String, Integer> innerMap = selectedMap.get(subwayOption);
				if(innerMap.size() < 3)
				{
					for(String value : innerMap.keySet())
					{
						System.out.println( subwayOption +"     -  " + value + "          -  " + innerMap.get(value) + " ₹");
						totalAmount += innerMap.get(value);
					}
				}
				else
				{
					HashMap<String, Integer> sortedMap = sortByValue(innerMap); // sorting the map for get minimum range of topping
					ArrayList<String> arlist = new ArrayList();
	                                for(String key: sortedMap.keySet())
        	                                arlist.add(key);
					String sortedKey = arlist.get(arlist.size()-1);
					sortedMap.replace(sortedKey,0);
					for(Map.Entry m : sortedMap.entrySet())
					{
						System.out.println("Topping" + "        -  " + m.getKey() + "          -  " + m.getValue() + " ₹");
						totalAmount += (Integer)m.getValue();
					}
				}
			}

			System.out.println(" Total                   -  " + totalAmount + " ₹");
		}
		catch(Exception e)
                {
                        e.printStackTrace();
                }
                return totalAmount;
	}
	private static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    	{
        // Create a list from elements of HashMap
	         List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
	        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
        	    public int compare(Map.Entry<String, Integer> o1,
                	               Map.Entry<String, Integer> o2)
	            {
        	        return (o2.getValue()).compareTo(o1.getValue());
	            }
        	});

        // put data from sorted list to hashmap
	        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        	for (Map.Entry<String, Integer> aa : list) {
	            temp.put(aa.getKey(), aa.getValue());
	        }
        	return temp;
        }
}

/*
 * Copyright (C) 2004-2015 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q10366_RuinsStatusUpdate;

import org.l2junity.gameserver.enums.Race;
import org.l2junity.gameserver.model.actor.Npc;
import org.l2junity.gameserver.model.actor.instance.PlayerInstance;
import org.l2junity.gameserver.model.quest.Quest;
import org.l2junity.gameserver.model.quest.QuestState;
import org.l2junity.gameserver.model.quest.State;
import org.l2junity.gameserver.network.client.send.ExShowScreenMessage;
import org.l2junity.gameserver.network.client.send.string.NpcStringId;

import quests.Q10365_SeekerEscort.Q10365_SeekerEscort;

/**
 * Ruins Status Update (10366)
 * @author Gladicek
 */
public final class Q10366_RuinsStatusUpdate extends Quest
{
	// NPCs
	private static final int SEBION = 32978;
	private static final int FRANCO = 32153;
	private static final int RIVIAN = 32147;
	private static final int DEVON = 32160;
	private static final int TOOK = 32150;
	private static final int MOKA = 32157;
	private static final int VALFAR = 32146;
	// Misc
	private static final int MIN_LEVEL = 16;
	private static final int MAX_LEVEL = 25;
	
	public Q10366_RuinsStatusUpdate()
	{
		super(10366);
		addStartNpc(SEBION);
		addTalkId(SEBION, FRANCO, RIVIAN, DEVON, TOOK, MOKA, VALFAR);
		addCondLevel(MIN_LEVEL, MAX_LEVEL, "32978-10.htm");
		addCondCompletedQuest(Q10365_SeekerEscort.class.getSimpleName(), "32978-10.htm");
	}
	
	@Override
	public String onAdvEvent(String event, Npc npc, PlayerInstance player)
	{
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return null;
		}
		
		String htmltext = null;
		
		switch (event)
		{
			case "check_race":
			{
				switch (player.getRace())
				{
					case HUMAN:
					{
						qs.startQuest();
						qs.setCond(2);
						htmltext = "32978-03.htm";
						showOnScreenMsg(player, NpcStringId.USE_THE_ESCAPE_SCROLL_IN_YOUR_INVENTORY_TO_GO_TO_THE_ADMINISTRATIVE_OFFICE_IN_TALKING_ISLAND, ExShowScreenMessage.TOP_CENTER, 4500);
						break;
					}
					case ELF:
					{
						qs.startQuest();
						qs.setCond(3);
						htmltext = "32978-04.htm";
						showOnScreenMsg(player, NpcStringId.USE_THE_ESCAPE_SCROLL_IN_YOUR_INVENTORY_TO_GO_TO_THE_ADMINISTRATIVE_OFFICE_IN_TALKING_ISLAND, ExShowScreenMessage.TOP_CENTER, 4500);
						break;
					}
					case DARK_ELF:
					{
						qs.startQuest();
						qs.setCond(4);
						htmltext = "32978-05.htm";
						showOnScreenMsg(player, NpcStringId.USE_THE_ESCAPE_SCROLL_IN_YOUR_INVENTORY_TO_GO_TO_THE_ADMINISTRATIVE_OFFICE_IN_TALKING_ISLAND, ExShowScreenMessage.TOP_CENTER, 4500);
						break;
					}
					case ORC:
					{
						qs.startQuest();
						qs.setCond(5);
						htmltext = "32978-06.htm";
						showOnScreenMsg(player, NpcStringId.USE_THE_ESCAPE_SCROLL_IN_YOUR_INVENTORY_TO_GO_TO_THE_ADMINISTRATIVE_OFFICE_IN_TALKING_ISLAND, ExShowScreenMessage.TOP_CENTER, 4500);
						break;
					}
					case DWARF:
					{
						qs.startQuest();
						qs.setCond(6);
						htmltext = "32978-07.htm";
						showOnScreenMsg(player, NpcStringId.USE_THE_ESCAPE_SCROLL_IN_YOUR_INVENTORY_TO_GO_TO_THE_ADMINISTRATIVE_OFFICE_IN_TALKING_ISLAND, ExShowScreenMessage.TOP_CENTER, 4500);
						break;
					}
					case KAMAEL:
					{
						qs.startQuest();
						qs.setCond(7);
						htmltext = "32978-08.htm";
						showOnScreenMsg(player, NpcStringId.USE_THE_ESCAPE_SCROLL_IN_YOUR_INVENTORY_TO_GO_TO_THE_ADMINISTRATIVE_OFFICE_IN_TALKING_ISLAND, ExShowScreenMessage.TOP_CENTER, 4500);
						break;
					}
					case ERTHEIA:
					{
						break; // Retail like NCZoft typo in quest...
					}
				}
				break;
			}
			case "32978-02.htm":
			case "32146-02.htm":
			case "32147-02.htm":
			case "32150-02.htm":
			case "32153-02.htm":
			case "32157-02.htm":
			case "32160-02.htm":
			{
				htmltext = event;
				break;
			}
			case "32146-03.htm":
			case "32147-03.htm":
			case "32150-03.htm":
			case "32153-03.htm":
			case "32157-03.htm":
			case "32160-03.htm":
			{
				if ((qs.getCond() >= 2) && (qs.getCond() <= 7))
				{
					giveAdena(player, 750, true);
					addExpAndSp(player, 150000, 36);
					qs.exitQuest(false, true);
				}
				htmltext = event;
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(Npc npc, PlayerInstance player)
	{
		final QuestState qs = getQuestState(player, true);
		String htmltext = null;
		
		switch (qs.getState())
		{
			case State.CREATED:
			{
				if (npc.getId() == SEBION)
				{
					htmltext = "32978-01.htm";
					break;
				}
				break;
			}
			case State.STARTED:
			{
				switch (npc.getId())
				{
					case SEBION:
					{
						switch (qs.getCond())
						{
							case 2:
							{
								htmltext = "32978-03.htm";
								break;
							}
							case 3:
							{
								htmltext = "32978-04.htm";
								break;
							}
							case 4:
							{
								htmltext = "32978-05.htm";
								break;
							}
							case 5:
							{
								htmltext = "32978-06.htm";
								break;
							}
							case 6:
							{
								htmltext = "32978-07.htm";
								break;
							}
							case 7:
							{
								htmltext = "32978-08.htm";
								break;
							}
						}
						break;
					}
					case FRANCO:
					{
						if (player.getRace() == Race.HUMAN)
						{
							if ((qs.isCond(2)))
							{
								htmltext = "32153-01.htm";
								break;
							}
						}
						htmltext = "32153-04.htm";
						break;
					}
					case RIVIAN:
					{
						if (player.getRace() == Race.ELF)
						{
							if ((qs.isCond(3)))
							{
								htmltext = "32147-01.htm";
								break;
							}
						}
						htmltext = "32147-04.htm";
						break;
					}
					case DEVON:
					{
						if (player.getRace() == Race.DARK_ELF)
						{
							if ((qs.isCond(4)))
							{
								htmltext = "32160-01.htm";
								break;
							}
						}
						htmltext = "32160-04.htm";
						break;
					}
					case TOOK:
					{
						if (player.getRace() == Race.ORC)
						{
							if ((qs.isCond(5)))
							{
								htmltext = "32150-01.htm";
								break;
							}
						}
						htmltext = "32150-04.htm";
						break;
					}
					case MOKA:
					{
						if (player.getRace() == Race.DWARF)
						{
							if ((qs.isCond(6)))
							{
								htmltext = "32157-01.htm";
								break;
							}
						}
						htmltext = "32157-04.htm";
						break;
					}
					case VALFAR:
					{
						if (player.getRace() == Race.KAMAEL)
						{
							if ((qs.isCond(7)))
							{
								htmltext = "32146-01.htm";
								break;
							}
						}
						htmltext = "32146-04.htm";
						break;
					}
				}
				break;
			}
			case State.COMPLETED:
			{
				switch (npc.getId())
				{
					case SEBION:
					{
						htmltext = "32978-09.htm";
						break;
					}
					case FRANCO:
					case RIVIAN:
					case DEVON:
					case TOOK:
					case MOKA:
					case VALFAR:
					{
						htmltext = npc.getId() + "-05.htm";
						break;
					}
				}
			}
		}
		return htmltext;
	}
}
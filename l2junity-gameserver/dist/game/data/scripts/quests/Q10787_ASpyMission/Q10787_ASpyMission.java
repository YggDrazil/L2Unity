/*
 * Copyright (C) 2004-2015 L2J Unity
 * 
 * This file is part of L2J Unity.
 * 
 * L2J Unity is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Unity is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q10787_ASpyMission;

import org.l2junity.gameserver.enums.Race;
import org.l2junity.gameserver.model.StatsSet;
import org.l2junity.gameserver.model.World;
import org.l2junity.gameserver.model.actor.Npc;
import org.l2junity.gameserver.model.actor.instance.PlayerInstance;
import org.l2junity.gameserver.model.quest.Quest;
import org.l2junity.gameserver.model.quest.QuestState;
import org.l2junity.gameserver.model.quest.State;

import quests.Q10786_ResidentProblemSolver.Q10786_ResidentProblemSolver;

/**
 * A Spy Mission (10787)
 * @author malyelfik
 */
public final class Q10787_ASpyMission extends Quest
{
	// NPCs
	private static final int SHUVANN = 33867;
	private static final int SUSPICIOUS_BOX = 33994;
	// Monster
	private static final int EMBRYO_PURIFIER = 27540;
	// Items
	private static final int ENCHANT_ARMOR_A = 26351;
	private static final int EMBRYO_MISSIVES = 39724;
	// Misc
	private static final int MIN_LEVEL = 61;
	private static final int MAX_LEVEL = 65;
	private static final int ITEMGET_CHANCE = 30;
	
	public Q10787_ASpyMission()
	{
		super(10787);
		addStartNpc(SHUVANN);
		addTalkId(SHUVANN, SUSPICIOUS_BOX);
		
		addCondRace(Race.ERTHEIA, "33867-00.html");
		addCondLevel(MIN_LEVEL, MAX_LEVEL, "33867-01.htm");
		addCondCompletedQuest(Q10786_ResidentProblemSolver.class.getSimpleName(), "33867-01.htm");
		registerQuestItems(EMBRYO_MISSIVES);
	}
	
	@Override
	public String onAdvEvent(String event, Npc npc, PlayerInstance player)
	{
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return null;
		}
		
		String htmltext = event;
		switch (event)
		{
			case "33867-03.htm":
			case "33867-04.htm":
				break;
			case "33867-05.htm":
				qs.startQuest();
				break;
			case "33994-02.html":
			{
				if (qs.isCond(1))
				{
					if (getRandom(100) < ITEMGET_CHANCE)
					{
						giveItems(player, EMBRYO_MISSIVES, 1);
						qs.setCond(2, true);
						htmltext = "33994-03.html";
					}
					// @formatter:off
					World.getInstance().getVisibleObjects(npc, Npc.class, 150).stream()
					.filter(n -> (n.getId() == EMBRYO_PURIFIER))
					.forEach(mob -> addAttackPlayerDesire(mob, player));
					// @formatter:on
					getTimers().addTimer("DESPAWN", 1000, npc, null);
				}
				break;
			}
			case "33867-08.html":
			{
				if (qs.isCond(2))
				{
					giveItems(player, ENCHANT_ARMOR_A, 5);
					giveStoryQuestReward(player, 29);
					if (player.getLevel() >= MIN_LEVEL)
					{
						addExpAndSp(player, 3125586, 750);
					}
					qs.exitQuest(false, true);
				}
				break;
			}
			default:
				htmltext = null;
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(Npc npc, PlayerInstance player)
	{
		final QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		
		if (npc.getId() == SHUVANN)
		{
			switch (qs.getState())
			{
				case State.CREATED:
					htmltext = "33867-02.htm";
					break;
				case State.STARTED:
					htmltext = (qs.isCond(1)) ? "33867-06.html" : "33867-07.html";
					break;
				case State.COMPLETED:
					htmltext = getAlreadyCompletedMsg(player);
					break;
			}
		}
		else if (qs.isStarted() && qs.isCond(1))
		{
			htmltext = "33994-01.html";
		}
		return htmltext;
	}
	
	@Override
	public void onTimerEvent(String event, StatsSet params, Npc npc, PlayerInstance player)
	{
		if ((npc != null) && (npc.getId() == SUSPICIOUS_BOX) && event.equals("DESPAWN"))
		{
			npc.deleteMe();
		}
		else
		{
			super.onTimerEvent(event, params, npc, player);
		}
	}
}
package itsu.mcbe.economysystemland.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.utils.TextFormat;
import itsu.mcbe.economysystem.api.EconomySystemAPI;
import itsu.mcbe.economysystemland.api.EconomySystemLandAPI;
import itsu.mcbe.economysystemland.api.ProtectedLand;
import itsu.mcbe.form.base.CustomForm;
import itsu.mcbe.form.base.ModalForm;
import itsu.mcbe.form.base.SimpleForm;
import itsu.mcbe.form.core.NukkitFormAPI;
import itsu.mcbe.form.element.Button;
import itsu.mcbe.form.element.Dropdown;

public class CommandProcessor {
	
	private Map<String, Integer[]> list = new HashMap<>();
	private EconomySystemLandAPI landAPI;
	private EconomySystemAPI api;
	private NukkitFormAPI formAPI;
	private int landPrice;
	private String unit;
	
	public CommandProcessor(NukkitFormAPI formAPI, EconomySystemAPI api, EconomySystemLandAPI landAPI, int landPrice) {
		this.formAPI = formAPI;
		this.api = api;
		this.landAPI = landAPI;
		this.landPrice = landPrice;
		this.unit = api.getUnit();
	}
	
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	Player player = null;
    	if(command.getName().equals("land")) {
    		if(isConsole(sender)) {
    			sender.sendMessage(TextFormat.RED + "ゲーム内から実行してください。");
    			return true;
    		}
    		
    		player = (Player) sender;
    		
    		try {
	    		switch(args[0]) {
	    		
		        	case "pos1":
		        		checkPlayer(player);
		        		setFirst(player);
		        		
		        		ProtectedLand land2 = null;
		        		if((land2 = landAPI.getLand((int) player.x, (int) player.z, player.getLevel().getName())) != null) {
		        			player.sendMessage(TextFormat.RED + "ここは" + land2.getOwner() + "さんが所有している土地のため購入できません！");
		        			return true;
		        		}
		        		
		        		player.sendMessage(TextFormat.GREEN + "第一地点を設定しました。（x: " + (int) player.x + ", z: " + (int) player.z + "）");
		        		
		        		if(!didNotSelected(player)) {
		        			player.sendMessage(TextFormat.GREEN + "/landで購入手続きに進みます。");
		        		}
		        		
		        		return true;
		        		
		        	case "pos2":
		        		checkPlayer(player);
		        		setSecond(player);
		        		
		        		ProtectedLand land1 = null;
		        		if((land1 = landAPI.getLand((int) player.x, (int) player.z, player.getLevel().getName())) != null) {
		        			player.sendMessage(TextFormat.RED + "ここは" + land1.getOwner() + "さんが所有している土地のため購入できません！");
		        			return true;
		        		}
		        		
		        		player.sendMessage(TextFormat.GREEN + "第二地点を設定しました。（x: " + (int) player.x + ", z: " + (int) player.z + "）");
		        		
		        		if(!didNotSelected(player)) {
		        			player.sendMessage(TextFormat.GREEN + "/landで購入手続きに進みます。");
		        		}
		        		
		        		return true;
		        		
		        	case "here":
		        		ProtectedLand land = null;
		        		
		        		if((land = landAPI.getLand((int) player.x, (int) player.z, player.getLevel().getName())) != null) {
		        			player.sendMessage(TextFormat.GREEN + "ここは" + land.getOwner() + "さんが所有している土地です。（土地番号: " + land.getId() + "）");
		        			return true;
		        		}
		        		
		        		player.sendMessage(TextFormat.GREEN + "誰も所有していません。");
		        		return true;
		        		
		        	case "buy":
		        		if(!didNotSelected(player)) {
		        			process(player);
		        			return true;
		        			
		        		} else {
		        			sender.sendMessage(TextFormat.RED + "座標を指定してください！");
		        			return true;
		        		}
		        		
		        	case "me":
		        		player.sendMessage(TextFormat.AQUA + "あなたが所有している土地一覧: ");
		        		landAPI.getPlayerLands(player.getName()).stream()
		        			.forEach(pLand -> sender.sendMessage(
		        				TextFormat.GREEN + "ID: " + TextFormat.WHITE + pLand.getId() + " " + 
		        				TextFormat.GREEN + "面積: " + TextFormat.WHITE + pLand.getSize() + " " + 
		        				TextFormat.GREEN + "座標: " + TextFormat.WHITE + ((pLand.getEndX() - pLand.getStartX()) / 2) + ", " + ((pLand.getEndZ() - pLand.getStartZ()) / 2) + "\n")
		        			);
		        		return true;
		        		
		        	case "info":
		        		try{
		        			if(args[1] != null){}
		        			
		        			ProtectedLand myLand = landAPI.getLandById(Integer.parseInt(args[1]));
		        			
		        			if(!myLand.getOwner().equals(player.getName())) {
		        				sender.sendMessage(TextFormat.RED + "他人の土地の情報は見れません！");
			        			return true;
		        			}
		        			
		        			player.sendMessage(TextFormat.AQUA + "土地情報 ID: " + args[1]);
		        			player.sendMessage(TextFormat.GREEN + "所有者: " + TextFormat.WHITE + myLand.getOwner());
		        			player.sendMessage(TextFormat.GREEN + "ワールド: " + TextFormat.WHITE + myLand.getWorld());
		        			player.sendMessage(TextFormat.GREEN + "面積: " + TextFormat.WHITE + myLand.getSize());
		        			player.sendMessage(TextFormat.GREEN + "座標: " + TextFormat.WHITE + ((myLand.getEndX() - myLand.getStartX()) / 2) + ", " + ((myLand.getEndZ() - myLand.getStartZ()) / 2));
		        			player.sendMessage(TextFormat.GREEN + "共有メンバー: " + myLand.getInvite().toString());
		        			
		        		} catch(IndexOutOfBoundsException ex) {
		        			player.sendMessage(TextFormat.RED + "土地番号を入力してください。");
		        			return true;
		        			
		        		} catch(NumberFormatException ex1) {
		        			player.sendMessage(TextFormat.RED + "土地番号は数値を入力してください。");
		        			return true;
		        		}
		        		
		        		return true;
		        		
		        		
		        	case "invite":
			        	try{
		        			if(args[1] != null){}
		        			if(args[2] != null){}
		        			
		        			ProtectedLand myLand = landAPI.getLandById(Integer.parseInt(args[1]));
		        			
		        			if(!myLand.getOwner().equals(player.getName())) {
		        				sender.sendMessage(TextFormat.RED + "他人の土地に招待することはできません！");
			        			return true;
		        			}
		        			
		        			if(landAPI.inviteLand(Integer.parseInt(args[1]), args[2])) {
		        				player.sendMessage(TextFormat.GREEN + args[2] + "さんと" + args[1] + "の土地を共有しました。");
		        			} else {
		        				player.sendMessage(TextFormat.RED + "共有に失敗しました。: 自分を招待した、すでに招待されている可能性があります。");
		        			}
		        			
		        		} catch(IndexOutOfBoundsException ex) {
		        			player.sendMessage(TextFormat.RED + "土地番号またはプレイヤー名を入力してください。");
		        			return true;
		        			
		        		} catch(NumberFormatException ex1) {
		        			player.sendMessage(TextFormat.RED + "土地番号は数値を入力してください。");
		        			return true;
		        		}
		        		
		        		return true;
		        		
		        	case "uninvite":
			        	try{
		        			if(args[1] != null){}
		        			if(args[2] != null){}
		        			
		        			ProtectedLand myLand = landAPI.getLandById(Integer.parseInt(args[1]));
		        			
		        			if(!myLand.getOwner().equals(player.getName())) {
		        				sender.sendMessage(TextFormat.RED + "他人の土地に招待することはできません！");
			        			return true;
		        			}
		        			
		        			if(landAPI.unInviteLand(Integer.parseInt(args[1]), args[2])) {
		        				player.sendMessage(TextFormat.GREEN + args[2] + "さんと" + args[1] + "の土地の共有を解除しました。");
		        			} else {
		        				player.sendMessage(TextFormat.RED + "共有解除に失敗しました。: 自分を指定した、指定されたプレイヤーが招待されてない可能性があります。");
		        			}
		        			
		        		} catch(IndexOutOfBoundsException ex) {
		        			player.sendMessage(TextFormat.RED + "土地番号またはプレイヤー名を入力してください。");
		        			return true;
		        			
		        		} catch(NumberFormatException ex1) {
		        			player.sendMessage(TextFormat.RED + "土地番号は数値を入力してください。");
		        			return true;
		        		}
		        		
		        		return true;
	    		}
	    		
    		} catch(IndexOutOfBoundsException e) {
    			Button b1 = new Button(TextFormat.DARK_BLUE + "土地を購入する") {
    				@Override
    				public void onClick(Player p) {
    					if(!didNotSelected(p)) {
		        			process(p);
		        			
						} else {
							ModalForm form = new ModalForm(830198);
							form.setTitle("EconomySystemLand 購入");
							form.setButton1Text("はい");
							form.setButton2Text("Close");
							form.setContent(TextFormat.RED + "先に/land pos1, land pos2で範囲指定を行ってください。");
							formAPI.sendFormToPlayer(p, form);
						}
    				}
    			};
    			
    			Button b2 = new Button(TextFormat.DARK_BLUE + "現在地の情報") {
    				@Override
    				public void onClick(Player p) {
						ModalForm form = new ModalForm(830199);
						form.setTitle("EconomySystemLand 現在地");
						form.setButton1Text("はい");
						form.setButton2Text("Close");
						
						ProtectedLand land = null;
		        		
		        		if((land = landAPI.getLand((int) p.x, (int) p.z, p.getLevel().getName())) != null) {
		        			form.setContent(TextFormat.GREEN + "ここは" + land.getOwner() + "さんが所有している土地です。（土地番号: " + land.getId() + "）");
		        		} else {
		        			form.setContent(TextFormat.GREEN + "誰も所有していません。");
		        		}
		        		
						formAPI.sendFormToPlayer(p, form);
    				}
    			};
    			
    			Button b3 = new Button(TextFormat.DARK_BLUE + "所有している土地") {
    				@Override
    				public void onClick(Player p) {
						SimpleForm form = new SimpleForm(830200);
						form.setTitle("EconomySystemLand 所有地一覧");
						form.setContent("ここに自分が所有している土地の一覧が表示されます。\nクリックで詳細を表示します。");
						
						landAPI.getPlayerLands(p.getName()).stream()
		        			.forEach(pLand -> form.addButton(
		        				new Button(){
		        					
			        				@Override
			        				public void onClick(Player pp) {
			        					int id = Integer.parseInt(this.getText().split("\n")[0].replaceAll("[^0-9]", "").substring(1));
					        			
					        			ModalForm form = new ModalForm(830201) {
					        				@Override
					        				public void onButton1Click(Player ppp) {
					        					CustomForm form = new CustomForm(830202) {
					        						@Override
					        						public void onEnter(Player p1, List<Object> response) {
					        							String name = (String) response.get(0);
					        							
					        							if(landAPI.inviteLand(id, name)) {
					        								p1.sendMessage(TextFormat.GREEN + name + "さんと" + id + "の土地を共有しました。");
					        								
					        		        			} else {
					        		        				p1.sendMessage(TextFormat.RED + "共有に失敗しました。: 自分を招待した、すでに招待されている可能性があります。");
					        		        			}
					        							
					        							String delName = (String) response.get(1);
					        							if(delName.equals("誰も解除しない")) return;
					        							
					        							if(landAPI.unInviteLand(id, delName)) {
					        								p1.sendMessage(TextFormat.GREEN + delName + "さんと" + id + "の土地の共有を解除しました。");
					        		        			
					        							} else {
					        								p1.sendMessage(TextFormat.RED + "共有解除に失敗しました。: 自分を指定した、指定されたプレイヤーが招待されてない可能性があります。");
					        		        			}
					        						}
					        					}
					        					
					        					.setTitle("EconomySystemLand 共有");
					        					
					        					Dropdown down = new Dropdown();
					        					down.setText("共有したいプレイヤーを選択してください。");
					        					
					        					Server.getInstance().getOnlinePlayers().values()
					        						.stream()
					        						.filter(p2 -> !pLand.getInvite().contains(p2.getName()))
					        						.forEach(p3 -> down.addOption(p3.getName()));
					        					
					        					form.addFormElement(down);
					        					
					        					Dropdown down1 = new Dropdown();
					        					down.setText("共有を解除したいプレイヤーを選択してください。");
					        					down.addOption("誰も解除しない");
					        					pLand.getInvite().forEach(nam -> down1.addOption(nam));
					        					form.addFormElement(down);
					        					
					        					formAPI.sendFormToPlayer(ppp, form);
					        				}
					        			};
					        			
										form.setTitle("EconomySystemLand 購入");
										form.setButton1Text("共有オプション");
										form.setButton2Text("閉じる");
										
			        					ProtectedLand myLand = landAPI.getLandById(id);
										StringBuffer buf = new StringBuffer();
					        			buf.append(TextFormat.AQUA + "土地情報 ID: " + id);
					        			buf.append(TextFormat.GREEN + "\n所有者: " + TextFormat.WHITE + myLand.getOwner());
					        			buf.append(TextFormat.GREEN + "\nワールド: " + TextFormat.WHITE + myLand.getWorld());
					        			buf.append(TextFormat.GREEN + "\n面積: " + TextFormat.WHITE + myLand.getSize());
					        			buf.append(TextFormat.GREEN + "\n座標: " + TextFormat.WHITE + ((myLand.getEndX() - myLand.getStartX()) / 2) + ", " + ((myLand.getEndZ() - myLand.getStartZ()) / 2));
					        			buf.append(TextFormat.GREEN + "\n共有メンバー: " + TextFormat.WHITE + myLand.getInvite().toString());
					        			
					        			form.setContent(buf.toString());
					        			formAPI.sendFormToPlayer(p, form);
			        				}
			        				
			        			}
		        					.setText(
			        					TextFormat.DARK_BLUE + "ID: " + TextFormat.WHITE + pLand.getId() + "\n" + 
			        					TextFormat.YELLOW + "面積: " + TextFormat.WHITE + pLand.getSize() + " / " + 
			        					TextFormat.DARK_GREEN + "座標: " + TextFormat.WHITE + ((pLand.getEndX() - pLand.getStartX()) / 2) + ", " + ((pLand.getEndZ() - pLand.getStartZ()) / 2) + "\n")
		        					)
		        			);
		        		
						formAPI.sendFormToPlayer(p, form);
    				}
    			};
    			
    			Button b4 = new Button(TextFormat.DARK_BLUE + "土地を売却する") {
    				@Override
    				public void onClick(Player p) {
    					SimpleForm form = new SimpleForm(830200);
						form.setTitle("EconomySystemLand 売却");
						form.setContent("売却したい土地を選んでください。");
						
						landAPI.getPlayerLands(p.getName()).stream()
		        			.forEach(pLand -> form.addButton(
		        				new Button(){
			        				@Override
			        				public void onClick(Player pp) {
			        					int id = Integer.parseInt(this.getText().split("\n")[0].replaceAll("[^0-9]", "").substring(1));
			        					int money = 0;
			        					api.increaseMoney(pp.getName(), money = landAPI.getLandById(id).getSize() * EconomySystemLandAPI.LAND_VALUE / 2);
			        					landAPI.deleteLand(id, pp.getName());
			        					pp.sendMessage(TextFormat.GREEN + "土地を売却し" + money + api.getUnit() + "増えました。");
			        				}
		        				}
		        			)
		        		);
		        		
						formAPI.sendFormToPlayer(p, form);
    				}
    			};
    			
    			SimpleForm window = new SimpleForm(830203);
    			window.setTitle("EconomySystemLand メニュー");
    			window.setContent(TextFormat.AQUA + "操作を選択してください。");
    			window.setButtons(new Button[]{b1, b2, b3, b4});
    			formAPI.sendFormToPlayer(player, window);
    			return true;
    		}
    	}
        return true;
    }
    
    private void checkPlayer(Player player) {
    	if(!list.containsKey(player.getName())) {
    		list.put(player.getName(), new Integer[]{null, null, null, null});
    		return;
    	}
    }
    
    private void setFirst(Player player) {
    	list.get(player.getName())[0] = (int) player.x;
    	list.get(player.getName())[1] = (int) player.z;
    }
    
    private void setSecond(Player player) {
    	list.get(player.getName())[2] = (int) player.x;
    	list.get(player.getName())[3] = (int) player.z;
    }
    
    private void process(Player player) {
    	int startX = Math.min(list.get(player.getName())[0], list.get(player.getName())[2]);
    	int startZ = Math.min(list.get(player.getName())[1], list.get(player.getName())[3]);
    	int endX = Math.max(list.get(player.getName())[0], list.get(player.getName())[2]);
    	int endZ = Math.max(list.get(player.getName())[1], list.get(player.getName())[3]);
    	int size = (endX - startX) * (endZ - startZ);
    	int value = size * landPrice;
    	
    	StringBuffer buf = new StringBuffer();
    	buf.append(TextFormat.AQUA + "土地を購入しますか？\n");
    	buf.append(TextFormat.GREEN + "選択された土地の広さ" + TextFormat.WHITE + size + "ブロック\n");
    	buf.append(TextFormat.GREEN + "価格: " + TextFormat.WHITE + value + unit + "\n");
    	buf.append(TextFormat.GREEN + "所持金: " + TextFormat.WHITE + api.getMoney(player.getName()) + unit + "\n");
    	
    	ModalForm form = new ModalForm() {
    		@Override
    		public void onButton1Click(Player player) {
    			landAPI.createLand(startX, startZ, endX, endZ, size, player.getLevel().getName(), player.getName());
    			player.sendMessage(TextFormat.GREEN + "土地を購入しました。");
    		}
    		
    		@Override
    		public void onButton2Click(Player player) {
    			player.sendMessage(TextFormat.GREEN + "購入をキャンセルしました。");
    		}
    	}
    	
    	.setContent(buf.toString())
    	.setButton1Text("はい")
    	.setButton2Text("いいえ")
    	.setId(94837)
    	.setTitle("EconomySystemLand 購入");
    	
    	formAPI.sendFormToPlayer(player, form);
    }
    
    private boolean didNotSelected(Player player) {
    	if(list.get(player.getName()) == null) return true;
    	
    	for(Integer i : list.get(player.getName())) {
    		if(i == null) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private boolean isConsole(CommandSender sender) {
    	return sender instanceof ConsoleCommandSender ? true : false;
    }
    
}

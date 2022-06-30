package tech.inudev.profundus.define;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * GUIを構成するアイテムを表すクラス
 *
 * @author kumitatepazuru
 */
public class MenuItem {
    @Getter
    private final Component title;
    @Getter
    private List<Component> lore;

    public void setLore(List<Component> lore) {
        this.lore = lore;
        if (!isDraggable() && icon != null && lore != null) {
            ItemMeta meta = icon.getItemMeta();
            meta.lore(lore);
            icon.setItemMeta(meta);
        }
    }

    @Getter
    private final BiConsumer<MenuItem, Player> onClick;
    @Getter
    private ItemStack icon;

    public void setIcon(ItemStack icon) {
        this.icon = icon;

        if (this.icon == null) {
            if (draggable) {
                return;
            } else {
                throw new IllegalArgumentException("draggableがfalseの場合、iconをnullにはできません");
            }
        }
        if (draggable) {
            return;
        }

        if (shiny) {
            this.icon.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 1);
        }

        ItemMeta meta = this.icon.getItemMeta();
        if (title != null) {
            meta.displayName(title);
        }

        if (lore != null) {
            meta.lore(lore.size() > 0 ? lore : null);
        }
        this.icon.setItemMeta(meta);
    }

    @Getter
    private final Object customData;
    @Getter
    private final boolean shiny;
    @Getter
    private final boolean close;

    @Getter
    private final boolean draggable;

    /**
     * メニューのアイテム。
     *
     * @param title      アイテムのタイトル
     * @param lore       アイテムの説明
     * @param onClick    クリック時のイベント
     * @param icon       アイテムのブロック
     * @param customData Itemにつける任意のデータ
     * @param shiny      ブロックをキラキラさせるか
     * @param close      クリック時にGUIを閉じるか
     * @param draggable  アイテムをドラッグできるか(統合版のFromAPIでは動作しない)
     */
    public MenuItem(Component title, List<Component> lore, BiConsumer<MenuItem, Player> onClick, ItemStack icon, Object customData, boolean shiny, boolean close, boolean draggable) {
        this.title = title;
        this.lore = lore;
        this.onClick = onClick;
        this.customData = customData;
        this.shiny = shiny;
        this.close = close;
        this.draggable = draggable;
        setIcon(icon);
    }

    /**
     * メニューのアイテム。
     *
     * @param title      アイテムのタイトル
     * @param lore       アイテムの説明
     * @param onClick    クリック時のイベント
     * @param icon       アイテムのブロック
     * @param customData Itemにつける任意のデータ
     * @param shiny      ブロックをキラキラさせるか
     */
    public MenuItem(String title, List<Component> lore, BiConsumer<MenuItem, Player> onClick, ItemStack icon, Object customData, boolean shiny) {
        this(title != null ? Component.text(title) : null, lore, onClick, icon, customData, shiny, true, false);
    }

    /**
     * メニューのアイテム。
     *
     * @param title      アイテムのタイトル
     * @param lore       アイテムの説明
     * @param onClick    クリック時のイベント
     * @param icon       アイテムのブロック
     * @param customData Itemにつける任意のデータ
     */
    public MenuItem(String title, List<Component> lore, BiConsumer<MenuItem, Player> onClick, ItemStack icon, Object customData) {
        this(title, lore, onClick, icon, customData, false);
    }

    /**
     * メニューのアイテム。
     *
     * @param title   アイテムのタイトル
     * @param lore    アイテムの説明
     * @param onClick クリック時のイベント
     * @param icon    アイテムのブロック
     */
    public MenuItem(String title, List<Component> lore, BiConsumer<MenuItem, Player> onClick, ItemStack icon) {
        this(title, lore, onClick, icon, null);
    }

    /**
     * メニューのアイテム。
     *
     * @param title   アイテムのタイトル
     * @param onClick クリック時のイベント
     */
    public MenuItem(String title, BiConsumer<MenuItem, Player> onClick) {
        this(title, null, onClick, new ItemStack(Material.STONE));
    }

    /**
     * メニューのアイテム。
     *
     * @param title     アイテムのタイトル
     * @param onClick   クリック時のイベント
     * @param icon      アイテムのブロック
     * @param shiny     ブロックをキラキラさせるか
     * @param close     クリック時にGUIを閉じるか
     * @param draggable アイテムをドラッグできるか(統合版のFromAPIでは動作しない)
     */
    public MenuItem(String title, BiConsumer<MenuItem, Player> onClick, ItemStack icon, boolean shiny, boolean close, boolean draggable) {
        this(title != null ? Component.text(title) : null, null, onClick, icon, null, shiny, close, draggable);
    }

    /**
     * メニューのアイテム。
     *
     * @param title アイテムのタイトル
     */
    public MenuItem(String title) {
        this(title, null, null, new ItemStack(Material.STONE));
    }

    /**
     * メニューのアイテム。
     *
     * @param title   アイテムのタイトル
     * @param lore    アイテムの説明
     * @param onClick クリック時のイベント
     */
    public MenuItem(String title, List<Component> lore, BiConsumer<MenuItem, Player> onClick) {
        this(title, lore, onClick, new ItemStack(Material.STONE));
    }

    /**
     * メニューのアイテム。
     *
     * @param title     アイテムのタイトル
     * @param onClick   クリック時のイベント
     * @param icon      アイテムのブロック
     * @param close     クリック時にGUIを閉じるか
     * @param draggable アイテムをドラッグできるか(統合版のFromAPIでは動作しない)
     */
    public MenuItem(String title, BiConsumer<MenuItem, Player> onClick, ItemStack icon, boolean close, boolean draggable) {
        this(title != null ? Component.text(title) : null, null, onClick, icon, null, false, close, draggable);
    }


    /**
     * 不使用スロットを埋めるアイテムを生成する。
     *
     * @return 不使用スロットを埋めるアイテム
     */
    public static MenuItem generateDisuse(Player player) {
        ItemStack icon = new ItemStack(getDisuseMaterial(player));
        return new MenuItem(Component.text(""), null, null, icon, null, false, false, false);
    }

    private static Material getDisuseMaterial(Player player) {
        return Gui.isBedrock(player) ? Material.IRON_BARS : Material.GRAY_STAINED_GLASS_PANE;
    }

    public static MenuItem generateDraggable(BiConsumer<MenuItem, Player> onClick, ItemStack icon) {
        return new MenuItem(null, null, onClick, icon, null, false, false, true);
    }
}

 

import java.util.List;

public class WithId {

    private int id;

    public WithId() {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static int[] toIds(List<? extends WithId> withIds) {
        int[] ids = new int[withIds.size()];
        for (int i = 0; i < withIds.size(); i++) {
            ids[i] = withIds.get(i).getId();
        }
        return ids;
    }
}

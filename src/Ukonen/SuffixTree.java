package Ukonen;

import java.util.Map;

public class SuffixTree {
    Node root = new Node(-1, -1, null, null);
    Node lastNewNode = null;
    ActivePoint activeNode = new ActivePoint(null, '#', 0);
    int remainingSuffixCount = 0;
    int leafEnd = -1;
    int rootEnd = -1;
    int splitEnd = -1;
    int size;

    String line;

    void preorder(Node root) {//простое добавление
        Map<Integer, Node> leaves = root.getLeaves();
        if (leaves != null) {
            for (Node node : leaves.values()) {
                if (node.getLeaves() == null) {
                    node.setTo(node.getTo() + 1);
                } else {
                    preorder(node);
                }
            }
        }
    }

    /* изменение activePoint для перехода вниз (APCFWD) с помощью
    Пропустить / считать трюк (трюк 1). Если activeLength больше
    чем текущая длина ребра, установите следующий внутренний узел как
    activeNode и настройте activeEdge и activeLength
    соответственно представлять ту же ActivePoint */
    boolean walkDown(Node currentNode) {
        if (activeNode.length >= currentNode.length()) {
            activeNode.edge += currentNode.length();
            activeNode.length -= currentNode.length();
            activeNode.top = currentNode;
            return true;
        }
        return false;
    }

    void extendSuffixTree(int pos) {
 /* Правило расширения 1, оно заботится о расширении всех
        листья, созданные до сих пор в дереве */
        leafEnd = pos;
        preorder(root);
 /* Увеличивать оставшийсяSuffixCount, указывая, что
        добавлен новый суффикс в список суффиксов
        добавлено в дерево */
        remainingSuffixCount++;

 /* установить lastNewNode в NULL при запуске новой фазы,
 указывает на то, что внутренний узел не ожидает
 это суффикс сброса ссылки в текущей фазе */
        lastNewNode = null;
        // Добавить все суффиксы (еще не добавленные) по одному в дереве
        while (remainingSuffixCount > 0) {
            if (activeNode.length == 0) {
                activeNode.edge = line.charAt(pos) - 97;
            }// APCFALZ
            // Нет исходящего ребра, начинающегося с
            // activeEdge из activeNode

            if (activeNode.top.getLeaf(activeNode.edge) == null) {
                // Расширение правила 2 (создается новый край листа)
                activeNode.top.setLeaf(activeNode.edge, new Node(pos, leafEnd, null, null));

   /* Новый край листа создается в начале строки выше
   от существующего узла (текущий активный узел), и
   если есть какой-либо внутренний узел, ожидающий своего суффикса
   ссылка получить сброс, указать ссылку суффикса из этого последнего
   внутренний узел к текущему активному узлу. Затем установите lastNewNode
   значение NULL, указывающее, что больше нет узлов, ожидающих суффиксную ссылку
   сброс настроек. */
                if (lastNewNode != null) {
                    lastNewNode.setSuffixLink(activeNode.top);
                    lastNewNode = null;
                }
            }
            // Есть исходящий фронт, начинающийся с activeEdge
            // из activeNode
            else {

                // Получить следующий узел в конце начала ребра
                // с activeEdge
                Node next;
                if(activeNode.edge>=0) {
                    next = activeNode.top.getLeaf(line.charAt(activeNode.edge) - 97);
                }
                else{
                    next = activeNode.top.getLeaf(-62);
                }
                if (walkDown(next))// Пройдемся
                {
                    // Начать со следующего узла (новый активный узел)
                    continue;
                }
   /* Правило расширения 3 (текущий символ обрабатывается
                уже на грани) */
                if (line.charAt(next.getFrom() + activeNode.length) == line.charAt(pos)) {
                    // Если недавно созданный узел ожидает его
                    // суффиксная ссылка, которую нужно установить, затем установить суффиксную ссылку
                    // этого ожидающего узла к текущему активному узлу
                    if (lastNewNode != null && activeNode.top != root) {
                        lastNewNode.setSuffixLink(activeNode.top);
                        lastNewNode = null;
                    }
                    // APCFER3
                    activeNode.length++;

 /* ОСТАНОВИТЬ всю дальнейшую обработку на этом этапе
                    и перейти к следующему этапу */
                    break;
                }

   /* Мы будем здесь, когда activePoint будет в середине
                пройденный край и текущий символ
                обрабатывается не на краю (мы падаем
                дерево). В этом случае мы добавляем новый внутренний узел
                и новый край листа выходит из этого нового узла. Эта
                это расширение правила 2, где новый край листа и новый
                внутренний узел создан */
                splitEnd = next.getFrom() + activeNode.length - 1;
                // Новый внутренний узел
                Node split = new Node(next.getFrom(), splitEnd, null, root);
                activeNode.top.setLeaf(line.charAt(next.getFrom()) - 97, split);
                // Новый лист выходит из нового внутреннего узла
                split.setLeaf(line.charAt(pos) - 97, new Node(pos, leafEnd, null, root));
                next.setFrom(next.getFrom() + activeNode.length);
                split.setLeaf(line.charAt(next.getFrom()) - 97, next);

   /* Мы получили новый внутренний узел здесь. Если есть
                внутренний узел создан в последних расширениях того же
                фаза, которая все еще ждет своего суффикса
                сбросьте, сделайте это сейчас. */
                if (lastNewNode != null) {
   /* СуффиксLink из lastNewNode указывает на текущий недавно
                    создан внутренний узел */
                    lastNewNode.setSuffixLink(split);
                }

   /* Сделать текущий вновь созданный внутренний узел ожидающим
                для сброса ссылки суффикса (который указывает на root
                в настоящий момент). Если мы сталкиваемся с любым другим внутренним узлом
                (существующий или вновь созданный) в следующем расширении того же
                фаза, когда добавляется новый край листа (т.е. когда
                Расширение Правило 2 применяется к любому следующему расширению
                той же фазы) в этой точке, СуффиксЛинк этого узла
                будет указывать на этот внутренний узел. */
                lastNewNode = split;
            }

  /* В дерево добавлен один суффикс, уменьшающий счетчик
            суффиксы еще предстоит добавить. */

                remainingSuffixCount--;


            if (activeNode.top == root && activeNode.length > 0)
            {
                activeNode.length--;
                activeNode.edge = line.charAt(pos - remainingSuffixCount + 1) - 97;
            } else {
                if (activeNode.top != root)
                {
                    activeNode.top = activeNode.top.getSuffixLink();
                }
            }
        }
    }

    void buildSuffixTree(String line) {
        this.line = line;
        size = line.length();
        int i;
        rootEnd = -1;
        activeNode.top = root;// Первый активный узел будет корневым
        for (i = 0; i < size; i++) {
            extendSuffixTree(i);
        }
    }

    void print(int i, int j) {
        int k;
        for (k = i; k <= j; k++)
            System.out.print( line.charAt(k));
        System.out.print(" ");

    }
    // Выводим также дерево суффиксов вместе с настройкой индекса суффикса
    // Таким образом, дерево будет напечатано в DFS-стиле
    // Каждое ребро вместе с индексом суффикса будет напечатано

    void setSuffixIndexByDFS(Node n, String place) {
        if (n == null)
            return;
        if (n .getFrom() != -1) // некорневой узел
        {
            // Распечатать метку по краю от родителя к текущему узлу
            System.out.print(place+"|");
            print(n.getFrom(), n.getTo());

        }
        int leaf = 1;
        //int i;
        Map<Integer,Node> leaves = n.getLeaves();
        //String empty="";
        if(leaves!=null) {
            for (Node child : leaves.values()) {

                    if (leaf == 1 && n.getTo() != -1) {
                       // print(child.getFrom(), child.getTo());
                        System.out.println("not leaf");
                        place+=" ";
                    }
                    // Текущий узел не является листом, так как имеет исходящий
                    // края от него.
                    leaf = 0;
                    setSuffixIndexByDFS(child,place);
            }
        }
        if (leaf == 1) {
            System.out.println("leaf");
        }

    }
}

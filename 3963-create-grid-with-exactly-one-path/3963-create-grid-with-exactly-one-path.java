class Solution {
    public String[] createGrid(int m, int n) {

        String[] grid = new String[m]        ;



        // step 1 fill the every row with  "#''

        for(int i = 0; i<m; i++){
            char[]row = new char[n];

            for(int j = 0; j <n; j++){
                row[j] = '#';
            }


            grid[i] = new String(row);

        }

        //step2  make the first row free 


        char[] firstRow = new char[n];

        for (int j = 0; j<n; j++){
            firstRow[j] = '.';
        }

        grid[0] = new String(firstRow);

        // step 3 make the last colume free 
        

        for(int i = 1; i < m; i++){
            char[] row = grid[i].toCharArray();
            row[n-1] = '.';
            grid[i] = new String(row);




        }

        return grid;






    }
}